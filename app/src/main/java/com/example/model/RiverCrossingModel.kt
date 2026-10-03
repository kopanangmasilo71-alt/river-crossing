package com.example.model

import com.example.R

enum class Bank(val displayName: String, val shortName: String) {
    LEFT("Left Bank (Start)", "L"),
    RIGHT("Right Bank (Goal)", "R");

    fun opposite(): Bank = if (this == LEFT) RIGHT else LEFT
}

enum class GameItem(
    val id: String,
    val displayName: String,
    val emoji: String,
    val description: String,
    val dangerTag: String,
    val drawableRes: Int,
    val visualScale: Float = 1.0f
) {
    DOG(
        id = "dog",
        displayName = "Dog",
        emoji = "🐕",
        description = "Chases Rabbit/Sheep & fights Fox if left alone without Farmer!",
        dangerTag = "Predator",
        drawableRes = R.drawable.img_dog
    ),
    FOX(
        id = "fox",
        displayName = "Fox",
        emoji = "🦊",
        description = "Eats Rabbit & fights with Dog if left alone!",
        dangerTag = "Wild Predator",
        drawableRes = R.drawable.img_fox
    ),
    RABBIT(
        id = "rabbit",
        displayName = "Rabbit",
        emoji = "🐇",
        description = "Eats Cabbage, Corn & Hay if left alone, but vulnerable to predators!",
        dangerTag = "Herbivore",
        drawableRes = R.drawable.img_rabbit,
        visualScale = 1.08f
    ),
    CABBAGE(
        id = "cabbage",
        displayName = "Cabbage",
        emoji = "🥬",
        description = "Gets eaten by Rabbit or Sheep if left alone together!",
        dangerTag = "Fresh Crop",
        drawableRes = R.drawable.img_cabbage
    ),
    CORN(
        id = "corn",
        displayName = "Corn",
        emoji = "🌽",
        description = "Gets eaten by Rabbit or Sheep if left alone together!",
        dangerTag = "Golden Grain",
        drawableRes = R.drawable.img_corn
    ),
    WOLF(
        id = "wolf",
        displayName = "Wolf",
        emoji = "🐺",
        description = "Fierce carnivore that attacks Sheep, Dogs & Rabbits if unattended!",
        dangerTag = "Fierce Predator",
        drawableRes = R.drawable.img_wolf
    ),
    SHEEP(
        id = "sheep",
        displayName = "Sheep",
        emoji = "🐑",
        description = "Gentle grazer that eats Hay, Corn & Cabbage, but prey to Wolves & Lions!",
        dangerTag = "Docile Grazer",
        drawableRes = R.drawable.img_sheep
    ),
    HAY(
        id = "hay",
        displayName = "Hay",
        emoji = "🌾",
        description = "Sweet dry forage that gets eaten by Sheep & Rabbits if left alone!",
        dangerTag = "Dry Forage",
        drawableRes = R.drawable.img_hay
    ),
    LION(
        id = "lion",
        displayName = "Lion",
        emoji = "🦁",
        description = "Apex king that attacks Wolves, Dogs & Sheep if left unattended!",
        dangerTag = "Apex Predator",
        drawableRes = R.drawable.img_lion
    ),
    BEAR(
        id = "bear",
        displayName = "Bear",
        emoji = "🐻",
        description = "Forest titan that craves Berries and clashes with Wolves and Dogs!",
        dangerTag = "Forest Titan",
        drawableRes = R.drawable.img_bear
    ),
    BERRIES(
        id = "berries",
        displayName = "Berries",
        emoji = "🫐",
        description = "Fresh basket of wild berries that Bear and Rabbits will greedily feast upon!",
        dangerTag = "Wild Fruit",
        drawableRes = R.drawable.img_berries
    )
}

enum class ItemLocation {
    LEFT_BANK,
    IN_BOAT,
    RIGHT_BANK
}

data class DangerRule(
    val predator: GameItem,
    val prey: GameItem,
    val title: String,
    val reason: String,
    val emoji: String
)

data class ImpendingConflict(
    val predator: GameItem?,
    val prey: GameItem?,
    val title: String,
    val description: String,
    val bank: Bank?,
    val isBoatConflict: Boolean = false
)

enum class PuzzleDifficulty(
    val title: String,
    val badgeColorHex: Long,
    val badgeTextColorHex: Long,
    val iconEmoji: String
) {
    NORMAL("Normal", 0xFFDCFCE7, 0xFF15803D, "🌱"),
    MEDIUM("Medium", 0xFFE0F2FE, 0xFF0369A1, "🌊"),
    HARD("Hard", 0xFFFEF3C7, 0xFFB45309, "🔥"),
    EXPERT("Expert", 0xFFFEE2E2, 0xFFB91C1C, "⚡")
}

enum class DifficultyMode(
    val id: String,
    val title: String,
    val shortName: String,
    val subtitle: String,
    val iconEmoji: String,
    val badgeColorHex: Long,
    val badgeTextColorHex: Long
) {
    STANDARD(
        id = "standard",
        title = "Standard Mode",
        shortName = "Standard",
        subtitle = "Classic rules with full shore visibility",
        iconEmoji = "🌱",
        badgeColorHex = 0xFFDCFCE7,
        badgeTextColorHex = 0xFF15803D
    ),
    FOG_OF_WAR(
        id = "fog_of_war",
        title = "Mystery Shore",
        shortName = "Hidden Items",
        subtitle = "Opposite bank is shrouded in fog until docked",
        iconEmoji = "🌫️",
        badgeColorHex = 0xFFE0E7FF,
        badgeTextColorHex = 0xFF4338CA
    ),
    RESTRICTED_COMBOS(
        id = "restricted_combos",
        title = "Restricted Cargo",
        shortName = "Restricted",
        subtitle = "Forbids volatile animal or heavy cargo pairings in boat",
        iconEmoji = "⛔",
        badgeColorHex = 0xFFFEF3C7,
        badgeTextColorHex = 0xFFB45309
    ),
    EXTREME(
        id = "extreme",
        title = "Extreme Challenge",
        shortName = "Extreme",
        subtitle = "Hidden Items + Restricted Cargo for master solvers",
        iconEmoji = "⚡",
        badgeColorHex = 0xFFFEE2E2,
        badgeTextColorHex = 0xFFB91C1C
    ),
    CUSTOM(
        id = "custom",
        title = "Custom Modifiers",
        shortName = "Custom",
        subtitle = "Tailor challenge variations to your preference",
        iconEmoji = "⚙️",
        badgeColorHex = 0xFFF3E8FF,
        badgeTextColorHex = 0xFF7E22CE
    )
}

data class DifficultyModifiers(
    val mode: DifficultyMode = DifficultyMode.STANDARD,
    val hideOppositeBankItems: Boolean = false,
    val restrictBoatCombinations: Boolean = false,
    val maxMovesLimit: Int? = null
) {
    companion object {
        fun fromMode(mode: DifficultyMode): DifficultyModifiers {
            return when (mode) {
                DifficultyMode.STANDARD -> DifficultyModifiers(
                    mode = DifficultyMode.STANDARD,
                    hideOppositeBankItems = false,
                    restrictBoatCombinations = false
                )
                DifficultyMode.FOG_OF_WAR -> DifficultyModifiers(
                    mode = DifficultyMode.FOG_OF_WAR,
                    hideOppositeBankItems = true,
                    restrictBoatCombinations = false
                )
                DifficultyMode.RESTRICTED_COMBOS -> DifficultyModifiers(
                    mode = DifficultyMode.RESTRICTED_COMBOS,
                    hideOppositeBankItems = false,
                    restrictBoatCombinations = true
                )
                DifficultyMode.EXTREME -> DifficultyModifiers(
                    mode = DifficultyMode.EXTREME,
                    hideOppositeBankItems = true,
                    restrictBoatCombinations = true
                )
                DifficultyMode.CUSTOM -> DifficultyModifiers(
                    mode = DifficultyMode.CUSTOM,
                    hideOppositeBankItems = false,
                    restrictBoatCombinations = false
                )
            }
        }
    }

    /**
     * Evaluates if a list of passengers violates boat cargo restrictions.
     * In restricted mode, certain volatile or restless pairings cannot share the boat:
     * - Dog + Fox: Volatile rival predators fight in the boat
     * - Dog + Rabbit: Predator attacks prey inside the tight vessel
     * - Fox + Rabbit: Predator attacks prey inside the tight vessel
     * - Rabbit + Cabbage/Corn: Herbivore devours crop during the crossing
     */
    fun getBoatCombinationViolation(passengers: List<GameItem>): String? {
        if (!restrictBoatCombinations || passengers.size < 2) return null

        val hasDog = GameItem.DOG in passengers
        val hasFox = GameItem.FOX in passengers
        val hasRabbit = GameItem.RABBIT in passengers
        val hasCabbage = GameItem.CABBAGE in passengers
        val hasCorn = GameItem.CORN in passengers
        val hasWolf = GameItem.WOLF in passengers
        val hasSheep = GameItem.SHEEP in passengers
        val hasHay = GameItem.HAY in passengers
        val hasLion = GameItem.LION in passengers
        val hasBear = GameItem.BEAR in passengers
        val hasBerries = GameItem.BERRIES in passengers

        return when {
            hasLion && (hasWolf || hasDog || hasSheep || hasBear) ->
                "⚠️ Restricted Cargo: Lion will attack other passengers inside the small boat!"
            hasBear && (hasWolf || hasDog || hasSheep || hasBerries) ->
                "⚠️ Restricted Cargo: Bear will fight or devour cargo inside the boat!"
            hasWolf && (hasDog || hasFox || hasSheep || hasRabbit) ->
                "⚠️ Restricted Cargo: Wolf will fight or attack cargo inside the boat!"
            hasDog && hasFox ->
                "⚠️ Restricted Cargo: Dog and Fox will fight violently inside the small boat!"
            hasDog && (hasRabbit || hasSheep) ->
                "⚠️ Restricted Cargo: Dog and prey cannot share the boat without commotion!"
            hasFox && (hasRabbit || hasBerries) ->
                "⚠️ Restricted Cargo: Fox will attack prey or scatter berries inside the rowboat!"
            hasSheep && (hasHay || hasCabbage || hasCorn) ->
                "⚠️ Restricted Cargo: Sheep will devour the feed during transit!"
            hasRabbit && (hasCabbage || hasCorn || hasHay || hasBerries) ->
                "⚠️ Restricted Cargo: Rabbit will nibble the crops and berries during transit!"
            else -> null
        }
    }

    fun isBoatCombinationAllowed(passengers: List<GameItem>): Boolean {
        return getBoatCombinationViolation(passengers) == null
    }
}

data class PuzzleScenario(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val difficulty: PuzzleDifficulty,
    val items: List<GameItem>,
    val boatCapacity: Int = 1,
    val optimalMoves: Int,
    val targetTimeSeconds: Long = 60L,
    val dangerRules: List<DangerRule>,
    val description: String,
    val rules: List<String>
)

object PuzzleScenarios {
    // Level 1: Basics
    val LEVEL_1 = PuzzleScenario(
        id = "level_1_basics",
        levelNumber = 1,
        title = "Level 1: The Basics",
        subtitle = "Get the Rabbit and Cabbage across!",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 1,
        optimalMoves = 3,
        targetTimeSeconds = 40L,
        dangerRules = listOf(
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left the Rabbit and Cabbage alone together on the {bank} without the Farmer.", "🐇💥🥬")
        ),
        description = "A simple introductory riddle. Transport the hungry rabbit and fresh cabbage across safely.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Rabbit will eat Cabbage if left alone on a bank."
        )
    )

    // Level 2: The Classic
    val LEVEL_2 = PuzzleScenario(
        id = "level_2_classic",
        levelNumber = 2,
        title = "Level 2: The Classic",
        subtitle = "Farmer, Dog, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left the Dog and the Rabbit alone together on the {bank} without the Farmer.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left the Rabbit and the Cabbage alone together on the {bank} without the Farmer.", "🐇💥🥬")
        ),
        description = "The timeless river crossing riddle known throughout history.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Dog will chase Rabbit if left unattended.",
            "Rabbit will eat Cabbage if left unattended."
        )
    )

    // Level 3: Professional
    val LEVEL_3 = PuzzleScenario(
        id = "level_3_pro",
        levelNumber = 3,
        title = "Level 3: Professional",
        subtitle = "Wolf, Dog, Rabbit, and Cabbage!",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.WOLF, GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Fought The Dog!", "You left the Wolf and Dog together unattended on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left the Dog and Rabbit together unattended on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left the Rabbit and Cabbage together unattended on the {bank}.", "🐇💥🥬")
        ),
        description = "Four items linked in a chain of hostility across the river.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf fights Dog, Dog chases Rabbit, Rabbit eats Cabbage."
        )
    )

    // Level 4: The Sheep
    val LEVEL_4 = PuzzleScenario(
        id = "level_4_sheep",
        levelNumber = 4,
        title = "Level 4: The Sheep",
        subtitle = "Wolf, Sheep, and Hay",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.WOLF, GameItem.SHEEP, GameItem.HAY),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left the Wolf and Sheep alone together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left the Sheep and sweet Hay alone together on the {bank}.", "🐑💥🌾")
        ),
        description = "Protect the gentle sheep from the timber wolf while keeping the dry hay safe.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Wolf attacks Sheep if left unattended.",
            "Sheep devours Hay if left unattended."
        )
    )

    // Level 5: Wild Safari
    val LEVEL_5 = PuzzleScenario(
        id = "level_5_safari",
        levelNumber = 5,
        title = "Level 5: Wild Safari",
        subtitle = "Lion, Sheep, and Rabbit",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.LION, GameItem.SHEEP, GameItem.RABBIT),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.SHEEP, "Lion Attacked The Sheep!", "You left the Lion and Sheep alone together on the {bank}.", "🦁💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.RABBIT, "Sheep Trampled The Rabbit!", "You left the Sheep and Rabbit alone together on the {bank}.", "🐑💥🐇")
        ),
        description = "An exotic safari dilemma with the king of beasts.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Lion attacks Sheep if unattended.",
            "Sheep and Rabbit cannot share a bank alone."
        )
    )

    // Level 6: The Guard
    val LEVEL_6 = PuzzleScenario(
        id = "level_6_guard",
        levelNumber = 6,
        title = "Level 6: The Guard",
        subtitle = "Lion, Dog, and Rabbit",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.LION, GameItem.DOG, GameItem.RABBIT),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.DOG, "Lion Clashed With Dog!", "You left the Lion and Dog together unattended on the {bank}.", "🦁💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left the Dog and Rabbit together unattended on the {bank}.", "🐕💥🐇")
        ),
        description = "The loyal guard dog stands between the lion and the rabbit.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Lion clashes with Dog without Farmer.",
            "Dog chases Rabbit without Farmer."
        )
    )

    // Level 7: Garden Keeper
    val LEVEL_7 = PuzzleScenario(
        id = "level_7_garden",
        levelNumber = 7,
        title = "Level 7: Garden Keeper",
        subtitle = "Rabbit, Cabbage, and Hay",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.RABBIT, GameItem.CABBAGE, GameItem.HAY),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left the Rabbit and Cabbage together unattended on the {bank}.", "🐇💥🥬"),
            DangerRule(GameItem.RABBIT, GameItem.HAY, "Rabbit Ate The Hay!", "You left the Rabbit and Hay together unattended on the {bank}.", "🐇💥🌾")
        ),
        description = "A greedy rabbit desires both fresh greens and sweet golden hay.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Rabbit will eat both Cabbage and Hay if left alone."
        )
    )

    // Level 8: Double Trouble
    val LEVEL_8 = PuzzleScenario(
        id = "level_8_double_trouble",
        levelNumber = 8,
        title = "Level 8: Double Trouble",
        subtitle = "Wolf, Dog, Sheep, and Hay",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.WOLF, GameItem.DOG, GameItem.SHEEP, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Fought The Dog!", "You left the Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left the Dog and Sheep together on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left the Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Four items linked by predator-prey tension across the river.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf fights Dog, Dog chases Sheep, Sheep eats Hay."
        )
    )

    // Level 9: Safari Night
    val LEVEL_9 = PuzzleScenario(
        id = "level_9_safari_night",
        levelNumber = 9,
        title = "Level 9: Safari Night",
        subtitle = "Lion, Wolf, and Sheep",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.SHEEP),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left the Lion and Wolf together unattended on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left the Wolf and Sheep together unattended on the {bank}.", "🐺💥🐑")
        ),
        description = "Two fierce carnivores and one defenseless sheep under moonlight.",
        rules = listOf(
            "Rowboat holds Farmer + 1 item at a time.",
            "Lion attacks Wolf, Wolf attacks Sheep."
        )
    )

    // Level 10: Total Chaos
    val LEVEL_10 = PuzzleScenario(
        id = "level_10_total_chaos",
        levelNumber = 10,
        title = "Level 10: Total Chaos",
        subtitle = "Lion, Dog, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.DOG, "Lion Fought The Dog!", "You left the Lion and Dog together unattended on the {bank}.", "🦁💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left the Dog and Rabbit together unattended on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left the Rabbit and Cabbage together unattended on the {bank}.", "🐇💥🥬")
        ),
        description = "Manage four distinct links in the food chain simultaneously.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion fights Dog, Dog chases Rabbit, Rabbit eats Cabbage."
        )
    )

    // Level 11: Harvest Crossing
    val LEVEL_11 = PuzzleScenario(
        id = "level_11_harvest",
        levelNumber = 11,
        title = "Level 11: Harvest Crossing",
        subtitle = "Dog, Sheep, Hay, and Corn",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.DOG, GameItem.SHEEP, GameItem.HAY, GameItem.CORN),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left the Dog and Sheep alone on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left the Sheep and Hay alone on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left the Sheep and Corn alone on the {bank}.", "🐑💥🌽")
        ),
        description = "Protect the prized golden corn and hay harvest from the hungry sheep.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Dog chases Sheep, Sheep eats both Hay and Corn."
        )
    )

    // Level 12: Predator Chain
    val LEVEL_12 = PuzzleScenario(
        id = "level_12_predator_chain",
        levelNumber = 12,
        title = "Level 12: Predator Chain",
        subtitle = "Lion, Wolf, Fox, and Sheep",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.FOX, GameItem.SHEEP),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.SHEEP, "Fox Harassed The Sheep!", "You left Fox and Sheep together on the {bank}.", "🦊💥🐑")
        ),
        description = "A layered hierarchy of fierce predators across the waterway.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Wolf, Wolf attacks Fox, Fox attacks Sheep."
        )
    )

    // Level 13: The Grand Pasture
    val LEVEL_13 = PuzzleScenario(
        id = "level_13_grand_pasture",
        levelNumber = 13,
        title = "Level 13: Grand Pasture",
        subtitle = "Wolf, Sheep, Cabbage, and Hay",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.WOLF, GameItem.SHEEP, GameItem.CABBAGE, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.CABBAGE, "Sheep Ate The Cabbage!", "You left Sheep and Cabbage together on the {bank}.", "🐑💥🥬"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Keep both the cabbage and hay stacks intact while shielding the sheep.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf attacks Sheep, Sheep eats both Cabbage and Hay."
        )
    )

    // Level 14: Fox in the Meadow
    val LEVEL_14 = PuzzleScenario(
        id = "level_14_fox_meadow",
        levelNumber = 14,
        title = "Level 14: Fox in the Meadow",
        subtitle = "Fox, Rabbit, Sheep, and Corn",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.FOX, GameItem.RABBIT, GameItem.SHEEP, GameItem.CORN),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Ate The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CORN, "Rabbit Ate The Corn!", "You left Rabbit and Corn together on the {bank}.", "🐇💥🌽"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽")
        ),
        description = "Two herbivores targeting the same crop while a clever fox watches.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Fox eats Rabbit, Rabbit and Sheep both eat Corn."
        )
    )

    // Level 15: Big Cat & Canines
    val LEVEL_15 = PuzzleScenario(
        id = "level_15_cat_canines",
        levelNumber = 15,
        title = "Level 15: Big Cat & Canines",
        subtitle = "Lion, Wolf, Dog, and Sheep",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.DOG, GameItem.SHEEP),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Attacked The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left Dog and Sheep together on the {bank}.", "🐕💥🐑")
        ),
        description = "Three tiers of carnivores and one innocent woolly sheep.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Wolf, Wolf attacks Dog, Dog chases Sheep."
        )
    )

    // Level 16: Triple Harvest
    val LEVEL_16 = PuzzleScenario(
        id = "level_16_triple_harvest",
        levelNumber = 16,
        title = "Level 16: Triple Harvest",
        subtitle = "Dog, Rabbit, Cabbage, Corn, and Hay",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE, GameItem.CORN, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬"),
            DangerRule(GameItem.RABBIT, GameItem.CORN, "Rabbit Ate The Corn!", "You left Rabbit and Corn together on the {bank}.", "🐇💥🌽"),
            DangerRule(GameItem.RABBIT, GameItem.HAY, "Rabbit Ate The Hay!", "You left Rabbit and Hay together on the {bank}.", "🐇💥🌾")
        ),
        description = "A gigantic harvest of three distinct crops and a hungry rabbit.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Dog chases Rabbit, Rabbit eats Cabbage, Corn, and Hay."
        )
    )

    // Level 17: Woodland Trail
    val LEVEL_17 = PuzzleScenario(
        id = "level_17_woodland",
        levelNumber = 17,
        title = "Level 17: Woodland Trail",
        subtitle = "Wolf, Fox, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.WOLF, GameItem.FOX, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Ate The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Transport the woodland forest animals without incident.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf attacks Fox, Fox eats Rabbit, Rabbit eats Cabbage."
        )
    )

    // Level 18: Savannah Expedition
    val LEVEL_18 = PuzzleScenario(
        id = "level_18_savannah",
        levelNumber = 18,
        title = "Level 18: Savannah Expedition",
        subtitle = "Lion, Sheep, Rabbit, and Hay",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.SHEEP, GameItem.RABBIT, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.SHEEP, "Lion Attacked The Sheep!", "You left Lion and Sheep together on the {bank}.", "🦁💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.RABBIT, GameItem.HAY, "Rabbit Ate The Hay!", "You left Rabbit and Hay together on the {bank}.", "🐇💥🌾")
        ),
        description = "Two grazing herbivores keen on the hay bale under the lion's watchful gaze.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Sheep, Sheep & Rabbit both devour Hay."
        )
    )

    // Level 19: Double Trouble Duos
    val LEVEL_19 = PuzzleScenario(
        id = "level_19_trouble_duos",
        levelNumber = 19,
        title = "Level 19: Double Duos",
        subtitle = "Wolf, Dog, Sheep, and Rabbit",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.WOLF, GameItem.DOG, GameItem.SHEEP, GameItem.RABBIT),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Fought The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇")
        ),
        description = "Two predators and two vulnerable farm friends.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf fights Dog & attacks Sheep, Dog chases Rabbit."
        )
    )

    // Level 20: Farmstead Shield
    val LEVEL_20 = PuzzleScenario(
        id = "level_20_farmstead",
        levelNumber = 20,
        title = "Level 20: Farmstead Shield",
        subtitle = "Dog, Fox, Rabbit, Corn, and Hay",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.DOG, GameItem.FOX, GameItem.RABBIT, GameItem.CORN, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 65L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.FOX, "Dog Fought The Fox!", "You left Dog and Fox together on the {bank}.", "🐕💥🦊"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Ate The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CORN, "Rabbit Ate The Corn!", "You left Rabbit and Corn together on the {bank}.", "🐇💥🌽"),
            DangerRule(GameItem.RABBIT, GameItem.HAY, "Rabbit Ate The Hay!", "You left Rabbit and Hay together on the {bank}.", "🐇💥🌾")
        ),
        description = "Shield multiple crops from the swift rabbit and keep the canines separated.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Dog fights Fox, Fox eats Rabbit, Rabbit eats Corn & Hay."
        )
    )

    // Level 21: King's Crossing
    val LEVEL_21 = PuzzleScenario(
        id = "level_21_kings_crossing",
        levelNumber = 21,
        title = "Level 21: King's Crossing",
        subtitle = "Lion, Wolf, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.RABBIT, "Wolf Devoured The Rabbit!", "You left Wolf and Rabbit together on the {bank}.", "🐺💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Royal predators and garden produce in delicate balance.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Wolf, Wolf devours Rabbit, Rabbit eats Cabbage."
        )
    )

    // Level 22: Desert Oasis
    val LEVEL_22 = PuzzleScenario(
        id = "level_22_desert_oasis",
        levelNumber = 22,
        title = "Level 22: Desert Oasis",
        subtitle = "Lion, Fox, Sheep, and Hay",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.FOX, GameItem.SHEEP, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.FOX, "Lion Attacked The Fox!", "You left Lion and Fox together on the {bank}.", "🦁💥🦊"),
            DangerRule(GameItem.FOX, GameItem.SHEEP, "Fox Harassed The Sheep!", "You left Fox and Sheep together on the {bank}.", "🦊💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Cross the oasis waters while balancing apex predators and fodder.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Fox, Fox harasses Sheep, Sheep eats Hay."
        )
    )

    // Level 23: The Granary Watch
    val LEVEL_23 = PuzzleScenario(
        id = "level_23_granary_watch",
        levelNumber = 23,
        title = "Level 23: Granary Watch",
        subtitle = "Wolf, Dog, Sheep, Hay, and Corn",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.WOLF, GameItem.DOG, GameItem.SHEEP, GameItem.HAY, GameItem.CORN),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 65L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Fought The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left Dog and Sheep together on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽")
        ),
        description = "Protect two separate grain stores while preventing a wolf-dog showdown.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Wolf fights Dog, Dog chases Sheep, Sheep eats Hay & Corn."
        )
    )

    // Level 24: Midnight Run
    val LEVEL_24 = PuzzleScenario(
        id = "level_24_midnight_run",
        levelNumber = 24,
        title = "Level 24: Midnight Run",
        subtitle = "Lion, Wolf, Fox, and Rabbit",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.FOX, GameItem.RABBIT),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Ate The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇")
        ),
        description = "The ultimate nighttime predator gauntlet across the deep river.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Wolf, Wolf attacks Fox, Fox eats Rabbit."
        )
    )

    // Level 25: The Five Factions
    val LEVEL_25 = PuzzleScenario(
        id = "level_25_five_factions",
        levelNumber = 25,
        title = "Level 25: The Five Factions",
        subtitle = "Lion, Wolf, Dog, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 70L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Fought The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "A 5-item linear food chain where every link must be carefully maintained.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion -> Wolf -> Dog -> Rabbit -> Cabbage danger chain."
        )
    )

    // Level 26: Golden Granary
    val LEVEL_26 = PuzzleScenario(
        id = "level_26_golden_granary",
        levelNumber = 26,
        title = "Level 26: Golden Granary",
        subtitle = "Dog, Rabbit, Sheep, Cabbage, Hay, and Corn",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.DOG, GameItem.RABBIT, GameItem.SHEEP, GameItem.CABBAGE, GameItem.HAY, GameItem.CORN),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 70L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽")
        ),
        description = "A wide 3-seater barge carries six items across the river!",
        rules = listOf(
            "Wide Barge holds Farmer + up to 3 items at once.",
            "Dog chases Rabbit, Rabbit eats Cabbage, Sheep eats Hay & Corn."
        )
    )

    // Level 27: Savannah & Forest
    val LEVEL_27 = PuzzleScenario(
        id = "level_27_savannah_forest",
        levelNumber = 27,
        title = "Level 27: Savannah & Forest",
        subtitle = "Lion, Wolf, Sheep, Rabbit, and Hay",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.SHEEP, GameItem.RABBIT, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 70L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.RABBIT, GameItem.HAY, "Rabbit Ate The Hay!", "You left Rabbit and Hay together on the {bank}.", "🐇💥🌾")
        ),
        description = "Harmonize savannah predators, forest wolves, and grazers.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion attacks Wolf, Wolf attacks Sheep, Sheep & Rabbit eat Hay."
        )
    )

    // Level 28: Mastermind Chain
    val LEVEL_28 = PuzzleScenario(
        id = "level_28_mastermind",
        levelNumber = 28,
        title = "Level 28: Mastermind Chain",
        subtitle = "Lion, Fox, Dog, Rabbit, and Cabbage",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.FOX, GameItem.DOG, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.FOX, "Lion Attacked The Fox!", "You left Lion and Fox together on the {bank}.", "🦁💥🦊"),
            DangerRule(GameItem.FOX, GameItem.DOG, "Fox Clashed With Dog!", "You left Fox and Dog together on the {bank}.", "🦊💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Intricate predator rivalry and prey protection across 5 entities.",
        rules = listOf(
            "Rowboat holds Farmer + up to 2 items at once.",
            "Lion -> Fox -> Dog -> Rabbit -> Cabbage."
        )
    )

    // Level 29: Ultimate Safari
    val LEVEL_29 = PuzzleScenario(
        id = "level_29_ultimate_safari",
        levelNumber = 29,
        title = "Level 29: Ultimate Safari",
        subtitle = "Lion, Wolf, Dog, Sheep, Hay, and Corn",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.WOLF, GameItem.DOG, GameItem.SHEEP, GameItem.HAY, GameItem.CORN),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Attacked The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left Dog and Sheep together on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽")
        ),
        description = "Command the 3-seater expedition ferry across deep savannah waters.",
        rules = listOf(
            "Large Ferry holds Farmer + up to 3 items at once.",
            "Lion attacks Wolf, Wolf attacks Dog, Dog chases Sheep, Sheep eats Hay & Corn."
        )
    )

    // Level 30: Grandmaster's River
    val LEVEL_30 = PuzzleScenario(
        id = "level_30_grandmaster",
        levelNumber = 30,
        title = "Level 30: Grandmaster's River",
        subtitle = "The Ultimate 8-Entity River Crossing",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(
            GameItem.LION,
            GameItem.WOLF,
            GameItem.FOX,
            GameItem.DOG,
            GameItem.SHEEP,
            GameItem.RABBIT,
            GameItem.CABBAGE,
            GameItem.HAY
        ),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 90L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.WOLF, "Lion Attacked The Wolf!", "You left Lion and Wolf together on the {bank}.", "🦁💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.DOG, "Fox Clashed With Dog!", "You left Fox and Dog together on the {bank}.", "🦊💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "The pinnacle challenge: 8 characters spanning every trophic level in an epic final crossing.",
        rules = listOf(
            "Barge holds Farmer + up to 3 items at once.",
            "Manage all 7 interwoven food-chain rules to achieve Grandmaster victory!"
        )
    )

    // Level 31: Bear's Feast
    val LEVEL_31 = PuzzleScenario(
        id = "level_31_bears_feast",
        levelNumber = 31,
        title = "Level 31: Bear's Feast",
        subtitle = "A Hungry Bear & Juicy Berries",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.BEAR, GameItem.BERRIES, GameItem.SHEEP),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.BEAR, GameItem.SHEEP, "Bear Chased The Sheep!", "You left Bear and Sheep together on the {bank}.", "🐻💥🐑")
        ),
        description = "The wild Bear loves berries and chases sheep. Keep them separated across the river.",
        rules = listOf("Boat holds Farmer + 1 passenger.", "Bear eats Berries and chases Sheep.")
    )

    // Level 32: Mountain Pasture
    val LEVEL_32 = PuzzleScenario(
        id = "level_32_mountain_pasture",
        levelNumber = 32,
        title = "Level 32: Mountain Pasture",
        subtitle = "Forage Across The Crags",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.BEAR, GameItem.BERRIES, GameItem.HAY),
        boatCapacity = 1,
        optimalMoves = 5,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐")
        ),
        description = "Transport the Bear, sweet mountain Berries, and dried Hay across the ridge stream.",
        rules = listOf("Boat holds Farmer + 1 passenger.", "Never leave Bear alone with Berries.")
    )

    // Level 33: Berry Patch Rivals
    val LEVEL_33 = PuzzleScenario(
        id = "level_33_berry_patch",
        levelNumber = 33,
        title = "Level 33: Berry Patch Rivals",
        subtitle = "Forest Foragers Collide",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.BEAR, GameItem.FOX, GameItem.BERRIES),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.FOX, GameItem.BERRIES, "Fox Scattered The Berries!", "You left Fox and Berries together on the {bank}.", "🦊💥🫐")
        ),
        description = "Both Bear and sneaky Fox are eyeing the sweet berry basket.",
        rules = listOf("Boat holds Farmer + 1 passenger.", "Keep Berries away from both Bear and Fox.")
    )

    // Level 34: Forest Banquet
    val LEVEL_34 = PuzzleScenario(
        id = "level_34_forest_banquet",
        levelNumber = 34,
        title = "Level 34: Forest Banquet",
        subtitle = "Two Feasts on the Riverbank",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.BERRIES, GameItem.RABBIT, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Double dining hazard! Bear craves berries while Rabbit hungers for cabbage.",
        rules = listOf("Ferry carries up to 2 items.", "Prevent both dining conflicts on either bank.")
    )

    // Level 35: Canine & Bruin
    val LEVEL_35 = PuzzleScenario(
        id = "level_35_canine_and_bruin",
        levelNumber = 35,
        title = "Level 35: Canine & Bruin",
        subtitle = "Territory & Temptation",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.DOG, GameItem.BERRIES, GameItem.RABBIT),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.BEAR, GameItem.DOG, "Bear Clashed With The Dog!", "You left Bear and Dog together on the {bank}.", "🐻💥🐕"),
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit together on the {bank}.", "🐕💥🐇")
        ),
        description = "Bear will brawl with the Dog and eat berries, while Dog chases Rabbit.",
        rules = listOf("Ferry carries up to 2 items.", "Manage Bear territory and Dog chases.")
    )

    // Level 36: Alpine Harvest
    val LEVEL_36 = PuzzleScenario(
        id = "level_36_alpine_harvest",
        levelNumber = 36,
        title = "Level 36: Alpine Harvest",
        subtitle = "High Mountain Shepherd",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.SHEEP, GameItem.BERRIES, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.SHEEP, "Bear Chased The Sheep!", "You left Bear and Sheep together on the {bank}.", "🐻💥🐑"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "A rugged shepherd crossing: protect sheep from the bear and hay from the sheep.",
        rules = listOf("Ferry carries up to 2 items.", "Bear attacks Sheep/Berries; Sheep eats Hay.")
    )

    // Level 37: Timber & Thorn
    val LEVEL_37 = PuzzleScenario(
        id = "level_37_timber_and_thorn",
        levelNumber = 37,
        title = "Level 37: Timber & Thorn",
        subtitle = "Apex Rivals of the North Woods",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.WOLF, GameItem.BERRIES, GameItem.SHEEP),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑")
        ),
        description = "Bear battles Wolf, Wolf hunts Sheep, and Bear craves the wild berries.",
        rules = listOf("Ferry carries up to 2 items.", "Prevent forest predator turf wars.")
    )

    // Level 38: Woodland Scavengers
    val LEVEL_38 = PuzzleScenario(
        id = "level_38_woodland_scavengers",
        levelNumber = 38,
        title = "Level 38: Woodland Scavengers",
        subtitle = "Fox, Rabbit & Bear Crossing",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.FOX, GameItem.RABBIT, GameItem.BERRIES),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.FOX, "Bear Swatted The Fox!", "You left Bear and Fox together on the {bank}.", "🐻💥🦊"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Pounced On The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇")
        ),
        description = "Fox hunts rabbit, bear swats fox, and sweet berries entice the mighty bear.",
        rules = listOf("Ferry carries up to 2 items.", "Safely transfer all 4 forest inhabitants.")
    )

    // Level 39: Highland Granary
    val LEVEL_39 = PuzzleScenario(
        id = "level_39_highland_granary",
        levelNumber = 39,
        title = "Level 39: Highland Granary",
        subtitle = "Multiple Feeds Across The Rapids",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.SHEEP, GameItem.BERRIES, GameItem.CORN, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.SHEEP, "Bear Chased The Sheep!", "You left Bear and Sheep together on the {bank}.", "🐻💥🐑"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Five cargo pieces! Sheep targets both crops, while Bear threatens both sheep and berries.",
        rules = listOf("Ferry carries up to 2 items.", "Keep ravenous Sheep away from Corn and Hay.")
    )

    // Level 40: Apex Showdown
    val LEVEL_40 = PuzzleScenario(
        id = "level_40_apex_showdown",
        levelNumber = 40,
        title = "Level 40: Apex Showdown",
        subtitle = "Lion Meets Bear",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.LION, GameItem.BEAR, GameItem.BERRIES, GameItem.SHEEP),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.BEAR, "Lion Clashed With The Bear!", "You left Lion and Bear together on the {bank}.", "🦁💥🐻"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.BEAR, GameItem.SHEEP, "Bear Chased The Sheep!", "You left Bear and Sheep together on the {bank}.", "🐻💥🐑"),
            DangerRule(GameItem.LION, GameItem.SHEEP, "Lion Hunted The Sheep!", "You left Lion and Sheep together on the {bank}.", "🦁💥🐑")
        ),
        description = "Two colossal predators on opposite food-chains. Sheep is coveted by both!",
        rules = listOf("Ferry carries up to 2 items.", "Never leave Lion with Bear or Sheep.")
    )

    // Level 41: Valley Guardians
    val LEVEL_41 = PuzzleScenario(
        id = "level_41_valley_guardians",
        levelNumber = 41,
        title = "Level 41: Valley Guardians",
        subtitle = "Bears, Wolves & Faithful Dogs",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.DOG, GameItem.WOLF, GameItem.BERRIES),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 65L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.WOLF, GameItem.DOG, "Wolf Attacked The Dog!", "You left Wolf and Dog together on the {bank}.", "🐺💥🐕")
        ),
        description = "Three mighty quadrupeds and sweet berries. Guard your loyal dog and forage.",
        rules = listOf("Ferry carries up to 2 items.", "Separate Bear from Wolf, and Wolf from Dog.")
    )

    // Level 42: Orchard Watch
    val LEVEL_42 = PuzzleScenario(
        id = "level_42_orchard_watch",
        levelNumber = 42,
        title = "Level 42: Orchard Watch",
        subtitle = "Triple Crop Preservation",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.RABBIT, GameItem.BERRIES, GameItem.CABBAGE, GameItem.CORN),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.BERRIES, "Rabbit Nibbled The Berries!", "You left Rabbit and Berries together on the {bank}.", "🐇💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬"),
            DangerRule(GameItem.RABBIT, GameItem.CORN, "Rabbit Chewed The Corn!", "You left Rabbit and Corn together on the {bank}.", "🐇💥🌽")
        ),
        description = "A voracious rabbit desires three different harvests, while the bear guards the berries.",
        rules = listOf("Ferry carries up to 2 items.", "Keep Rabbit away from Cabbage, Corn & Berries.")
    )

    // Level 43: Wilderness Trio
    val LEVEL_43 = PuzzleScenario(
        id = "level_43_wilderness_trio",
        levelNumber = 43,
        title = "Level 43: Wilderness Trio",
        subtitle = "Apex Predator Chain",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.WOLF, GameItem.FOX, GameItem.BERRIES),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 65L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐")
        ),
        description = "A strict hierarchy: Bear dominates Wolf, Wolf dominates Fox, and Berries tempt Bear.",
        rules = listOf("Ferry carries up to 2 items.", "Keep the cascading predator chain in check.")
    )

    // Level 44: High Alpine Patrol
    val LEVEL_44 = PuzzleScenario(
        id = "level_44_high_alpine_patrol",
        levelNumber = 44,
        title = "Level 44: High Alpine Patrol",
        subtitle = "Mountain Caravan Across The Stream",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.DOG, GameItem.SHEEP, GameItem.BERRIES, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 90L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.DOG, "Bear Clashed With The Dog!", "You left Bear and Dog together on the {bank}.", "🐻💥🐕"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left Dog and Sheep together on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Complex chain reaction: Bear fights Dog & Berries, Dog chases Sheep, Sheep eats Hay.",
        rules = listOf("Ferry carries up to 2 items.", "Requires careful 7-move sequencing to resolve.")
    )

    // Level 45: The Great Banquet
    val LEVEL_45 = PuzzleScenario(
        id = "level_45_the_great_banquet",
        levelNumber = 45,
        title = "Level 45: The Great Banquet",
        subtitle = "Interlinked Woodland Chain",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.FOX, GameItem.RABBIT, GameItem.BERRIES, GameItem.CABBAGE),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 90L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.BEAR, GameItem.FOX, "Bear Swatted The Fox!", "You left Bear and Fox together on the {bank}.", "🐻💥🦊"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Pounced On The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Four interlocking rules across 5 items. Shuttling back and forth requires foresight.",
        rules = listOf("Ferry carries up to 2 items.", "Master the 7-move return maneuver.")
    )

    // Level 46: Monarchs of the Wild
    val LEVEL_46 = PuzzleScenario(
        id = "level_46_monarchs_of_the_wild",
        levelNumber = 46,
        title = "Level 46: Monarchs of the Wild",
        subtitle = "Lion, Bear & Wolf Triumvirate",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.BEAR, GameItem.WOLF, GameItem.BERRIES, GameItem.SHEEP),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 85L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.BEAR, "Lion Clashed With The Bear!", "You left Lion and Bear together on the {bank}.", "🦁💥🐻"),
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑")
        ),
        description = "Three mighty carnivores vying for dominance, with innocent sheep and sweet berries.",
        rules = listOf("Ferry carries up to 2 items.", "Balance the triumvirate across the river.")
    )

    // Level 47: Five-Tier Biosphere
    val LEVEL_47 = PuzzleScenario(
        id = "level_47_five_tier_biosphere",
        levelNumber = 47,
        title = "Level 47: Five-Tier Biosphere",
        subtitle = "Multi-Entity Barge Expedition",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.BEAR, GameItem.WOLF, GameItem.SHEEP, GameItem.BERRIES, GameItem.HAY, GameItem.CORN),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 95L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.SHEEP, GameItem.CORN, "Sheep Ate The Corn!", "You left Sheep and Corn together on the {bank}.", "🐑💥🌽")
        ),
        description = "Six entities across the ecosystem. The 3-capacity barge provides room, but mistakes are fatal.",
        rules = listOf("Large Barge carries up to 3 items.", "Contain all 5 danger vectors.")
    )

    // Level 48: The Timberland Chain
    val LEVEL_48 = PuzzleScenario(
        id = "level_48_timberland_chain",
        levelNumber = 48,
        title = "Level 48: The Timberland Chain",
        subtitle = "Carnivores, Herbivores & Flora",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.BEAR, GameItem.WOLF, GameItem.FOX, GameItem.RABBIT, GameItem.BERRIES, GameItem.CABBAGE),
        boatCapacity = 3,
        optimalMoves = 3,
        targetTimeSeconds = 85L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Pounced On The Rabbit!", "You left Fox and Rabbit together on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.CABBAGE, "Rabbit Ate The Cabbage!", "You left Rabbit and Cabbage together on the {bank}.", "🐇💥🥬")
        ),
        description = "Six woodland members in a direct ecological chain from Bear down to Cabbage.",
        rules = listOf("Large Barge carries up to 3 items.", "Solve the intricate timberland food chain.")
    )

    // Level 49: Sovereign Sanctuary
    val LEVEL_49 = PuzzleScenario(
        id = "level_49_sovereign_sanctuary",
        levelNumber = 49,
        title = "Level 49: Sovereign Sanctuary",
        subtitle = "Regal Beasts & Mountain Herd",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.BEAR, GameItem.DOG, GameItem.SHEEP, GameItem.BERRIES, GameItem.HAY),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 100L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.BEAR, "Lion Clashed With The Bear!", "You left Lion and Bear together on the {bank}.", "🦁💥🐻"),
            DangerRule(GameItem.BEAR, GameItem.DOG, "Bear Clashed With The Dog!", "You left Bear and Dog together on the {bank}.", "🐻💥🐕"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.DOG, GameItem.SHEEP, "Dog Chased The Sheep!", "You left Dog and Sheep together on the {bank}.", "🐕💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Six majestic companions: Lion and Bear at the top, down to the humble bale of Hay.",
        rules = listOf("Large Barge carries up to 3 items.", "Harmonize the royal sanctuary crossing.")
    )

    // Level 50: The Master's Pantheon
    val LEVEL_50 = PuzzleScenario(
        id = "level_50_masters_pantheon",
        levelNumber = 50,
        title = "Level 50: The Master's Pantheon",
        subtitle = "The Grandest River Crossing in History",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(
            GameItem.LION,
            GameItem.BEAR,
            GameItem.WOLF,
            GameItem.FOX,
            GameItem.DOG,
            GameItem.SHEEP,
            GameItem.BERRIES,
            GameItem.HAY
        ),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 120L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.BEAR, "Lion Clashed With The Bear!", "You left Lion and Bear together on the {bank}.", "🦁💥🐻"),
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Fought The Wolf!", "You left Bear and Wolf together on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.FOX, "Wolf Attacked The Fox!", "You left Wolf and Fox together on the {bank}.", "🐺💥🦊"),
            DangerRule(GameItem.FOX, GameItem.DOG, "Fox Clashed With Dog!", "You left Fox and Dog together on the {bank}.", "🦊💥🐕"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "The ultimate 50-level milestone! 8 magnificent creatures in a symphony of river logistics.",
        rules = listOf(
            "Large Barge carries up to 3 items.",
            "Orchestrate 7 danger rules to conquer Level 50 and become the Ultimate River Grandmaster!"
        )
    )

    val SCENARIOS: List<PuzzleScenario> = listOf(
        LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5,
        LEVEL_6, LEVEL_7, LEVEL_8, LEVEL_9, LEVEL_10,
        LEVEL_11, LEVEL_12, LEVEL_13, LEVEL_14, LEVEL_15,
        LEVEL_16, LEVEL_17, LEVEL_18, LEVEL_19, LEVEL_20,
        LEVEL_21, LEVEL_22, LEVEL_23, LEVEL_24, LEVEL_25,
        LEVEL_26, LEVEL_27, LEVEL_28, LEVEL_29, LEVEL_30,
        LEVEL_31, LEVEL_32, LEVEL_33, LEVEL_34, LEVEL_35,
        LEVEL_36, LEVEL_37, LEVEL_38, LEVEL_39, LEVEL_40,
        LEVEL_41, LEVEL_42, LEVEL_43, LEVEL_44, LEVEL_45,
        LEVEL_46, LEVEL_47, LEVEL_48, LEVEL_49, LEVEL_50
    )

    val ALL = SCENARIOS

    val CLASSIC = LEVEL_2

    fun getById(id: String): PuzzleScenario = SCENARIOS.firstOrNull { it.id == id } ?: LEVEL_1
}

sealed class ViolationType(val title: String, val description: String, val emoji: String) {
    data class Custom(
        val bank: Bank,
        val customTitle: String,
        val customDescription: String,
        val customEmoji: String,
        val predator: GameItem? = null,
        val prey: GameItem? = null
    ) : ViolationType(customTitle, customDescription, customEmoji)

    data class DogEatsRabbit(val bank: Bank) : ViolationType(
        title = "Dog Ate The Rabbit!",
        description = "You left the Dog and the Rabbit alone together on the ${bank.displayName} without the Farmer.",
        emoji = "🐕💥🐇"
    )

    data class RabbitEatsCabbage(val bank: Bank) : ViolationType(
        title = "Rabbit Ate The Cabbage!",
        description = "You left the Rabbit and the Cabbage alone together on the ${bank.displayName} without the Farmer.",
        emoji = "🐇💥🥬"
    )
}

data class RiverState(
    val scenario: PuzzleScenario = PuzzleScenarios.LEVEL_1,
    val farmerBank: Bank = Bank.LEFT,
    val itemLocations: Map<GameItem, ItemLocation> = scenario.items.associateWith { ItemLocation.LEFT_BANK },
    val boatPassengers: List<GameItem> = emptyList(),
    val difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
) {
    val boatPassenger: GameItem? get() = boatPassengers.firstOrNull()

    val dogLocation: ItemLocation get() = getItemLocation(GameItem.DOG)
    val rabbitLocation: ItemLocation get() = getItemLocation(GameItem.RABBIT)
    val cabbageLocation: ItemLocation get() = getItemLocation(GameItem.CABBAGE)
    val foxLocation: ItemLocation get() = getItemLocation(GameItem.FOX)
    val cornLocation: ItemLocation get() = getItemLocation(GameItem.CORN)
    val wolfLocation: ItemLocation get() = getItemLocation(GameItem.WOLF)
    val sheepLocation: ItemLocation get() = getItemLocation(GameItem.SHEEP)
    val hayLocation: ItemLocation get() = getItemLocation(GameItem.HAY)
    val lionLocation: ItemLocation get() = getItemLocation(GameItem.LION)
    val bearLocation: ItemLocation get() = getItemLocation(GameItem.BEAR)
    val berriesLocation: ItemLocation get() = getItemLocation(GameItem.BERRIES)

    val bearPosition: ItemLocation get() = bearLocation
    val berriesPosition: ItemLocation get() = berriesLocation

    fun toGameState(): GameState = GameState.fromRiverState(this)

    fun isBankHidden(bank: Bank): Boolean {
        return difficultyModifiers.hideOppositeBankItems && farmerBank != bank
    }

    fun getItemLocation(item: GameItem): ItemLocation {
        return itemLocations[item] ?: ItemLocation.LEFT_BANK
    }

    fun withItemLocation(item: GameItem, location: ItemLocation): RiverState {
        return copy(itemLocations = itemLocations + (item to location))
    }

    fun isItemOnBank(item: GameItem, bank: Bank): Boolean {
        val loc = getItemLocation(item)
        return when (bank) {
            Bank.LEFT -> loc == ItemLocation.LEFT_BANK
            Bank.RIGHT -> loc == ItemLocation.RIGHT_BANK
        }
    }

    fun itemsOnBank(bank: Bank): List<GameItem> {
        return scenario.items.filter { isItemOnBank(it, bank) }
    }

    fun checkImpendingConflict(): ImpendingConflict? {
        if (boatPassengers.size > scenario.boatCapacity) {
            return ImpendingConflict(
                predator = null,
                prey = null,
                title = "Boat Overloaded!",
                description = "Capacity is ${scenario.boatCapacity} item(s). Invalid load!",
                bank = null,
                isBoatConflict = true
            )
        }

        val boatViolation = difficultyModifiers.getBoatCombinationViolation(boatPassengers)
        if (boatViolation != null) {
            val predator = boatPassengers.firstOrNull { it == GameItem.DOG || it == GameItem.WOLF || it == GameItem.FOX || it == GameItem.LION || it == GameItem.BEAR }
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
        val leftBehind = itemsOnBank(departingBank).filter { it !in boatPassengers }

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

    fun checkViolation(): ViolationType? {
        val unattendedBank = farmerBank.opposite()
        val bankItems = itemsOnBank(unattendedBank)

        for (rule in scenario.dangerRules) {
            if (rule.predator in bankItems && rule.prey in bankItems) {
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

    fun isGoal(): Boolean {
        return farmerBank == Bank.RIGHT &&
                scenario.items.all { getItemLocation(it) == ItemLocation.RIGHT_BANK } &&
                boatPassengers.isEmpty()
    }

    fun toSimpleStateString(): String {
        val itemsStr = scenario.items.joinToString(", ") { item ->
            val loc = getItemLocation(item)
            val code = when (loc) {
                ItemLocation.LEFT_BANK -> "L"
                ItemLocation.RIGHT_BANK -> "R"
                ItemLocation.IN_BOAT -> "B"
            }
            "${item.displayName[0]}:$code"
        }
        return "(${farmerBank.shortName}, $itemsStr)"
    }
}

enum class GameStatus {
    IDLE,
    ROWING,
    GAME_OVER,
    VICTORY
}

data class MoveRecord(
    val moveNumber: Int,
    val fromBank: Bank,
    val toBank: Bank,
    val passengers: List<GameItem>,
    val previousState: RiverState,
    val description: String
) {
    val passenger: GameItem? get() = passengers.firstOrNull()
}

data class SplashEvent(
    val id: Long = System.currentTimeMillis(),
    val targetBank: Bank
)

