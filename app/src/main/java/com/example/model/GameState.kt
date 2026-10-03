package com.example.model

/**
 * Class structure representing the complete Game State for the River Crossing puzzle.
 * Explicitly tracks the positions of the Farmer, Bear, Berries, and all other puzzle items,
 * and implements the full movement rules and safety constraints of the puzzle.
 */
data class GameState(
    val scenario: PuzzleScenario = PuzzleScenarios.LEVEL_1,
    val farmerBank: Bank = Bank.LEFT,
    val itemPositions: Map<GameItem, ItemLocation> = scenario.items.associateWith { ItemLocation.LEFT_BANK },
    val boatPassengers: List<GameItem> = emptyList(),
    val difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
) {
    val boatCapacity: Int get() = scenario.boatCapacity

    // Explicit positions for Bear and Berries as requested
    val bearPosition: ItemLocation get() = getItemPosition(GameItem.BEAR)
    val berriesPosition: ItemLocation get() = getItemPosition(GameItem.BERRIES)

    // Explicit positions for other standard puzzle characters
    val dogPosition: ItemLocation get() = getItemPosition(GameItem.DOG)
    val rabbitPosition: ItemLocation get() = getItemPosition(GameItem.RABBIT)
    val cabbagePosition: ItemLocation get() = getItemPosition(GameItem.CABBAGE)
    val foxPosition: ItemLocation get() = getItemPosition(GameItem.FOX)
    val cornPosition: ItemLocation get() = getItemPosition(GameItem.CORN)
    val wolfPosition: ItemLocation get() = getItemPosition(GameItem.WOLF)
    val sheepPosition: ItemLocation get() = getItemPosition(GameItem.SHEEP)
    val hayPosition: ItemLocation get() = getItemPosition(GameItem.HAY)
    val lionPosition: ItemLocation get() = getItemPosition(GameItem.LION)

    val boatPassenger: GameItem? get() = boatPassengers.firstOrNull()

    fun getItemPosition(item: GameItem): ItemLocation {
        return itemPositions[item] ?: ItemLocation.LEFT_BANK
    }

    fun isItemOnBank(item: GameItem, bank: Bank): Boolean {
        val pos = getItemPosition(item)
        return when (bank) {
            Bank.LEFT -> pos == ItemLocation.LEFT_BANK
            Bank.RIGHT -> pos == ItemLocation.RIGHT_BANK
        }
    }

    fun getItemsOnBank(bank: Bank): List<GameItem> {
        return scenario.items.filter { isItemOnBank(it, bank) }
    }

    fun withItemPosition(item: GameItem, position: ItemLocation): GameState {
        return copy(itemPositions = itemPositions + (item to position))
    }

    // -------------------------------------------------------------------------
    // MOVEMENT RULES IMPLEMENTATION
    // -------------------------------------------------------------------------

    /**
     * Rule: Can an item be boarded into the boat?
     * 1. Item must be part of the current scenario.
     * 2. Item must be currently located on the same bank as the Farmer and Boat.
     * 3. Item must not already be in the boat.
     * 4. Boat must have remaining passenger capacity.
     * 5. Cargo pairing must not violate difficulty cargo restrictions.
     */
    fun canBoard(item: GameItem): MoveValidationResult {
        if (item !in scenario.items) {
            return MoveValidationResult.Invalid("The ${item.displayName} is not part of this riddle.")
        }
        val currentLoc = getItemPosition(item)
        if (currentLoc == ItemLocation.IN_BOAT) {
            return MoveValidationResult.Invalid("The ${item.displayName} is already in the boat.")
        }
        val expectedLoc = if (farmerBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK
        if (currentLoc != expectedLoc) {
            return MoveValidationResult.Invalid("The ${item.displayName} is on the opposite shore! Boat is docked at ${farmerBank.displayName}.")
        }
        if (boatPassengers.size >= boatCapacity) {
            return MoveValidationResult.Invalid("The rowboat is already at maximum capacity ($boatCapacity item${if (boatCapacity > 1) "s" else ""})!")
        }

        // Check restricted combinations rule
        val hypotheticalPassengers = boatPassengers + item
        val comboViolation = difficultyModifiers.getBoatCombinationViolation(hypotheticalPassengers)
        if (comboViolation != null) {
            return MoveValidationResult.Invalid(comboViolation)
        }

        return MoveValidationResult.Valid
    }

    /**
     * Executes the boarding of an item according to movement rules.
     */
    fun boardItem(item: GameItem): GameState {
        require(canBoard(item) is MoveValidationResult.Valid) { "Cannot board ${item.displayName}: invalid move" }
        return copy(
            itemPositions = itemPositions + (item to ItemLocation.IN_BOAT),
            boatPassengers = boatPassengers + item
        )
    }

    /**
     * Rule: Can an item disembark from the boat?
     * Item must currently be in the boat.
     */
    fun canDisembark(item: GameItem): MoveValidationResult {
        if (item !in boatPassengers) {
            return MoveValidationResult.Invalid("The ${item.displayName} is not inside the boat.")
        }
        return MoveValidationResult.Valid
    }

    /**
     * Executes the disembarking of an item onto the current bank.
     */
    fun disembarkItem(item: GameItem): GameState {
        require(canDisembark(item) is MoveValidationResult.Valid) { "Cannot disembark ${item.displayName}: not in boat" }
        val targetBankLoc = if (farmerBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK
        return copy(
            itemPositions = itemPositions + (item to targetBankLoc),
            boatPassengers = boatPassengers.filter { it != item }
        )
    }

    /**
     * Disembarks all passengers from the boat onto the current bank.
     */
    fun disembarkAll(): GameState {
        val targetBankLoc = if (farmerBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK
        val updatedPositions = itemPositions.toMutableMap()
        for (p in boatPassengers) {
            updatedPositions[p] = targetBankLoc
        }
        return copy(
            itemPositions = updatedPositions,
            boatPassengers = emptyList()
        )
    }

    /**
     * Rule: Can the river be crossed?
     * Boat must not exceed capacity and cargo must not be in conflict.
     */
    fun canCrossRiver(): MoveValidationResult {
        if (boatPassengers.size > boatCapacity) {
            return MoveValidationResult.Invalid("The boat is overloaded! Capacity is $boatCapacity.")
        }
        val boatViolation = difficultyModifiers.getBoatCombinationViolation(boatPassengers)
        if (boatViolation != null) {
            return MoveValidationResult.Invalid(boatViolation)
        }
        return MoveValidationResult.Valid
    }

    /**
     * Executes the River Crossing move:
     * 1. Farmer and boat cross from current bank to opposite bank.
     * 2. Passengers in the boat cross and disembark on the destination bank.
     * 3. Evaluates unattended items on the departure bank for predator-prey violations
     *    (such as Bear eating Berries, Wolf eating Sheep, Dog chasing Rabbit, etc.).
     * 4. Evaluates if the goal state has been reached.
     */
    fun crossRiver(): MoveResult {
        val validation = canCrossRiver()
        if (validation is MoveValidationResult.Invalid) {
            return MoveResult.InvalidMove(validation.reason)
        }

        val destinationBank = farmerBank.opposite()
        val destLocation = if (destinationBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK

        val updatedPositions = itemPositions.toMutableMap()
        for (passenger in boatPassengers) {
            updatedPositions[passenger] = destLocation
        }

        val landingState = copy(
            farmerBank = destinationBank,
            itemPositions = updatedPositions,
            boatPassengers = emptyList()
        )

        // Check if unattended bank contains fatal predator-prey violations
        val violation = landingState.checkViolations()
        if (violation != null) {
            return MoveResult.Violation(landingState, violation)
        }

        return MoveResult.Success(landingState, isGoal = landingState.isGoal())
    }

    /**
     * Evaluates whether the unattended bank (the one without the Farmer) contains
     * any predator and prey left alone together.
     * Specifically handles Bear eating Berries, Wolf eating Sheep, Rabbit eating Cabbage, etc.
     */
    fun checkViolations(): ViolationType? {
        val unattendedBank = farmerBank.opposite()
        val itemsOnUnattendedBank = getItemsOnBank(unattendedBank)

        for (rule in scenario.dangerRules) {
            if (rule.predator in itemsOnUnattendedBank && rule.prey in itemsOnUnattendedBank) {
                return ViolationType.Custom(
                    bank = unattendedBank,
                    customTitle = rule.title,
                    customDescription = rule.reason.replace("{bank}", unattendedBank.displayName),
                    customEmoji = rule.emoji,
                    predator = rule.predator,
                    prey = rule.prey
                )
            }
        }
        return null
    }

    /**
     * Checks if departing the current bank will trigger an immediate conflict
     * between remaining unattended items.
     */
    fun checkImpendingConflict(): ImpendingConflict? {
        if (boatPassengers.size > boatCapacity) {
            return ImpendingConflict(
                predator = null,
                prey = null,
                title = "Boat Overloaded!",
                description = "Capacity is $boatCapacity item(s).",
                bank = null,
                isBoatConflict = true
            )
        }

        val boatViolation = difficultyModifiers.getBoatCombinationViolation(boatPassengers)
        if (boatViolation != null) {
            val predator = boatPassengers.firstOrNull {
                it == GameItem.DOG || it == GameItem.WOLF || it == GameItem.FOX ||
                        it == GameItem.LION || it == GameItem.BEAR
            }
            val prey = boatPassengers.firstOrNull { it != predator }
            return ImpendingConflict(
                predator = predator,
                prey = prey,
                title = "Incompatible Cargo!",
                description = boatViolation,
                bank = null,
                isBoatConflict = true
            )
        }

        val departingBank = farmerBank
        val leftBehind = getItemsOnBank(departingBank).filter { it !in boatPassengers }

        for (rule in scenario.dangerRules) {
            if (rule.predator in leftBehind && rule.prey in leftBehind) {
                return ImpendingConflict(
                    predator = rule.predator,
                    prey = rule.prey,
                    title = "${rule.predator.displayName.uppercase()} eats ${rule.prey.displayName.uppercase()}!",
                    description = "${rule.predator.displayName} & ${rule.prey.displayName} will be left unattended on ${departingBank.displayName}!",
                    bank = departingBank,
                    isBoatConflict = false
                )
            }
        }

        return null
    }

    /**
     * Victory condition: Farmer is on the RIGHT bank, all scenario items are on the RIGHT bank,
     * and the boat is empty.
     */
    fun isGoal(): Boolean {
        return farmerBank == Bank.RIGHT &&
                scenario.items.all { getItemPosition(it) == ItemLocation.RIGHT_BANK } &&
                boatPassengers.isEmpty()
    }

    fun isBankHidden(bank: Bank): Boolean {
        return difficultyModifiers.hideOppositeBankItems && farmerBank != bank
    }

    // -------------------------------------------------------------------------
    // CONVERSIONS WITH RIVERSTATE FOR SEAMLESS COMPATIBILITY
    // -------------------------------------------------------------------------

    fun toRiverState(): RiverState {
        return RiverState(
            scenario = scenario,
            farmerBank = farmerBank,
            itemLocations = itemPositions,
            boatPassengers = boatPassengers,
            difficultyModifiers = difficultyModifiers
        )
    }

    companion object {
        fun fromRiverState(riverState: RiverState): GameState {
            return GameState(
                scenario = riverState.scenario,
                farmerBank = riverState.farmerBank,
                itemPositions = riverState.itemLocations,
                boatPassengers = riverState.boatPassengers,
                difficultyModifiers = riverState.difficultyModifiers
            )
        }

        fun createInitial(
            scenario: PuzzleScenario = PuzzleScenarios.LEVEL_1,
            difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
        ): GameState {
            return GameState(
                scenario = scenario,
                farmerBank = Bank.LEFT,
                itemPositions = scenario.items.associateWith { ItemLocation.LEFT_BANK },
                boatPassengers = emptyList(),
                difficultyModifiers = difficultyModifiers
            )
        }
    }
}

sealed interface MoveValidationResult {
    object Valid : MoveValidationResult
    data class Invalid(val reason: String) : MoveValidationResult
}

sealed interface MoveResult {
    data class Success(val newState: GameState, val isGoal: Boolean) : MoveResult
    data class Violation(val newState: GameState, val violation: ViolationType) : MoveResult
    data class InvalidMove(val reason: String) : MoveResult
}
