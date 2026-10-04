package com.example.algorithm

import com.example.model.Bank
import com.example.model.DifficultyModifiers
import com.example.model.GameItem
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import java.util.ArrayDeque

/**
 * State representing positions of Farmer, Dog, Rabbit, Cabbage
 * Each entity is strictly on Bank.LEFT or Bank.RIGHT.
 */
data class DiscreteState(
    val farmer: Bank,
    val dog: Bank,
    val rabbit: Bank,
    val cabbage: Bank
) {
    fun isValid(): Boolean {
        // Left bank constraint
        if (farmer != Bank.LEFT) {
            if (dog == Bank.LEFT && rabbit == Bank.LEFT) return false
            if (rabbit == Bank.LEFT && cabbage == Bank.LEFT) return false
        }
        // Right bank constraint
        if (farmer != Bank.RIGHT) {
            if (dog == Bank.RIGHT && rabbit == Bank.RIGHT) return false
            if (rabbit == Bank.RIGHT && cabbage == Bank.RIGHT) return false
        }
        return true
    }

    fun isGoal(): Boolean {
        return farmer == Bank.RIGHT && dog == Bank.RIGHT && rabbit == Bank.RIGHT && cabbage == Bank.RIGHT
    }

    fun toLabel(): String {
        return "(${farmer.shortName}, ${dog.shortName}, ${rabbit.shortName}, ${cabbage.shortName})"
    }

    fun generateNextValidStates(): List<Pair<DiscreteState, GameItem?>> {
        val nextStates = mutableListOf<Pair<DiscreteState, GameItem?>>()
        val nextFarmer = farmer.opposite()

        // 1. Farmer crosses alone
        val aloneState = copy(farmer = nextFarmer)
        if (aloneState.isValid()) {
            nextStates.add(Pair(aloneState, null))
        }

        // 2. Farmer crosses with Dog (if dog is on same bank)
        if (dog == farmer) {
            val withDog = copy(farmer = nextFarmer, dog = nextFarmer)
            if (withDog.isValid()) {
                nextStates.add(Pair(withDog, GameItem.DOG))
            }
        }

        // 3. Farmer crosses with Rabbit (if rabbit is on same bank)
        if (rabbit == farmer) {
            val withRabbit = copy(farmer = nextFarmer, rabbit = nextFarmer)
            if (withRabbit.isValid()) {
                nextStates.add(Pair(withRabbit, GameItem.RABBIT))
            }
        }

        // 4. Farmer crosses with Cabbage (if cabbage is on same bank)
        if (cabbage == farmer) {
            val withCabbage = copy(farmer = nextFarmer, cabbage = nextFarmer)
            if (withCabbage.isValid()) {
                nextStates.add(Pair(withCabbage, GameItem.CABBAGE))
            }
        }

        return nextStates
    }
}

data class GenericDiscreteState(
    val farmer: Bank,
    val itemBanks: Map<GameItem, Bank>
) {
    fun isValid(scenario: PuzzleScenario): Boolean {
        val unattendedBank = farmer.opposite()
        val bankItems = itemBanks.filter { (_, bank) -> bank == unattendedBank }.keys

        for (rule in scenario.dangerRules) {
            if (rule.predator in bankItems && rule.prey in bankItems) {
                return false
            }
        }
        return true
    }

    fun isGoal(scenario: PuzzleScenario): Boolean {
        return farmer == Bank.RIGHT && scenario.items.all { itemBanks[it] == Bank.RIGHT }
    }

    fun generateNextValidStates(
        scenario: PuzzleScenario,
        difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
    ): List<Pair<GenericDiscreteState, List<GameItem>>> {
        val nextStates = mutableListOf<Pair<GenericDiscreteState, List<GameItem>>>()
        val nextFarmer = farmer.opposite()
        val availableOnBank = scenario.items.filter { itemBanks[it] == farmer }

        // 1. Farmer crosses alone
        val aloneState = copy(farmer = nextFarmer)
        if (aloneState.isValid(scenario)) {
            nextStates.add(Pair(aloneState, emptyList()))
        }

        // 2. Farmer crosses with 1 item
        for (item in availableOnBank) {
            val nextMap = itemBanks.toMutableMap()
            nextMap[item] = nextFarmer
            val nextState = copy(farmer = nextFarmer, itemBanks = nextMap)
            if (nextState.isValid(scenario)) {
                nextStates.add(Pair(nextState, listOf(item)))
            }
        }

        // 3. Farmer crosses with 2 items (if boatCapacity >= 2)
        if (scenario.boatCapacity >= 2 && availableOnBank.size >= 2) {
            for (i in 0 until availableOnBank.size) {
                for (j in i + 1 until availableOnBank.size) {
                    val item1 = availableOnBank[i]
                    val item2 = availableOnBank[j]
                    val pair = listOf(item1, item2)

                    // Check difficulty modifiers restricted combinations
                    if (difficultyModifiers.isBoatCombinationAllowed(pair)) {
                        val nextMap = itemBanks.toMutableMap()
                        nextMap[item1] = nextFarmer
                        nextMap[item2] = nextFarmer
                        val nextState = copy(farmer = nextFarmer, itemBanks = nextMap)
                        if (nextState.isValid(scenario)) {
                            nextStates.add(Pair(nextState, pair))
                        }
                    }
                }
            }
        }

        // 4. Farmer crosses with 3 items (if boatCapacity >= 3)
        if (scenario.boatCapacity >= 3 && availableOnBank.size >= 3) {
            for (i in 0 until availableOnBank.size) {
                for (j in i + 1 until availableOnBank.size) {
                    for (k in j + 1 until availableOnBank.size) {
                        val item1 = availableOnBank[i]
                        val item2 = availableOnBank[j]
                        val item3 = availableOnBank[k]
                        val triplet = listOf(item1, item2, item3)

                        if (difficultyModifiers.isBoatCombinationAllowed(triplet)) {
                            val nextMap = itemBanks.toMutableMap()
                            nextMap[item1] = nextFarmer
                            nextMap[item2] = nextFarmer
                            nextMap[item3] = nextFarmer
                            val nextState = copy(farmer = nextFarmer, itemBanks = nextMap)
                            if (nextState.isValid(scenario)) {
                                nextStates.add(Pair(nextState, triplet))
                            }
                        }
                    }
                }
            }
        }

        return nextStates
    }
}

data class SearchNode(
    val state: DiscreteState,
    val parent: SearchNode?,
    val itemMoved: GameItem?,
    val depth: Int
)

data class GenericSearchNode(
    val state: GenericDiscreteState,
    val parent: GenericSearchNode?,
    val itemsMoved: List<GameItem>,
    val depth: Int
)

object RiverCrossingSolver {

    val INITIAL_STATE = DiscreteState(Bank.LEFT, Bank.LEFT, Bank.LEFT, Bank.LEFT)
    val GOAL_STATE = DiscreteState(Bank.RIGHT, Bank.RIGHT, Bank.RIGHT, Bank.RIGHT)

    /**
     * Solves any arbitrary scenario using Breadth-First Search (BFS)
     */
    fun findGenericShortestPath(
        scenario: PuzzleScenario,
        startState: GenericDiscreteState,
        difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
    ): List<Pair<GenericDiscreteState, List<GameItem>>>? {
        if (!startState.isValid(scenario)) return null
        if (startState.isGoal(scenario)) return emptyList()

        val queue = ArrayDeque<GenericSearchNode>()
        val visited = mutableSetOf<GenericDiscreteState>()

        val root = GenericSearchNode(startState, null, emptyList(), 0)
        queue.add(root)
        visited.add(startState)

        while (queue.isNotEmpty()) {
            val current = queue.poll() ?: break

            if (current.state.isGoal(scenario)) {
                val path = mutableListOf<Pair<GenericDiscreteState, List<GameItem>>>()
                var curr: GenericSearchNode? = current
                while (curr?.parent != null) {
                    path.add(0, Pair(curr.state, curr.itemsMoved))
                    curr = curr.parent
                }
                return path
            }

            for ((nextState, itemsMoved) in current.state.generateNextValidStates(scenario, difficultyModifiers)) {
                if (nextState !in visited) {
                    visited.add(nextState)
                    queue.add(GenericSearchNode(nextState, current, itemsMoved, current.depth + 1))
                }
            }
        }

        return null
    }

    /**
     * Solves the puzzle from a starting state using Breadth-First Search (BFS)
     * Returning the shortest list of moves to reach the goal.
     */
    fun findShortestPath(startState: DiscreteState = INITIAL_STATE): List<Pair<DiscreteState, GameItem?>>? {
        if (!startState.isValid()) return null
        if (startState.isGoal()) return emptyList()

        val queue = ArrayDeque<SearchNode>()
        val visited = mutableSetOf<DiscreteState>()

        val root = SearchNode(startState, null, null, 0)
        queue.add(root)
        visited.add(startState)

        while (queue.isNotEmpty()) {
            val current = queue.poll() ?: break

            if (current.state.isGoal()) {
                val path = mutableListOf<Pair<DiscreteState, GameItem?>>()
                var curr: SearchNode? = current
                while (curr?.parent != null) {
                    path.add(0, Pair(curr.state, curr.itemMoved))
                    curr = curr.parent
                }
                return path
            }

            for ((nextState, itemMoved) in current.state.generateNextValidStates()) {
                if (nextState !in visited) {
                    visited.add(nextState)
                    queue.add(SearchNode(nextState, current, itemMoved, current.depth + 1))
                }
            }
        }

        return null
    }

    /**
     * Returns the recommended next move items from a given scenario state
     */
    fun getNextOptimalMoveForScenario(
        scenario: PuzzleScenario,
        startState: GenericDiscreteState,
        difficultyModifiers: DifficultyModifiers = DifficultyModifiers()
    ): List<GameItem>? {
        val path = findGenericShortestPath(scenario, startState, difficultyModifiers)
        return path?.firstOrNull()?.second
    }

    /**
     * Returns the recommended next move item from a given discrete state
     */
    fun getNextOptimalMove(currentState: DiscreteState): GameItem? {
        val path = findShortestPath(currentState)
        return path?.firstOrNull()?.second
    }

    /**
     * Generates all 16 potential states in the (F, D, R, C) state space
     */
    data class StateAnalysis(
        val allStates: List<DiscreteState>,
        val validStates: List<DiscreteState>,
        val invalidStates: List<DiscreteState>,
        val optimalPath: List<Pair<DiscreteState, GameItem?>>
    )

    fun analyzeStateSpace(): StateAnalysis {
        val all = mutableListOf<DiscreteState>()
        val banks = listOf(Bank.LEFT, Bank.RIGHT)

        for (f in banks) {
            for (d in banks) {
                for (r in banks) {
                    for (c in banks) {
                        all.add(DiscreteState(f, d, r, c))
                    }
                }
            }
        }

        val valid = all.filter { it.isValid() }
        val invalid = all.filter { !it.isValid() }
        val optimalPath = findShortestPath(INITIAL_STATE) ?: emptyList()

        return StateAnalysis(
            allStates = all,
            validStates = valid,
            invalidStates = invalid,
            optimalPath = optimalPath
        )
    }

    fun getMoveExplanation(stepIndex: Int, item: GameItem?, fromBank: Bank): String {
        return when (item) {
            GameItem.RABBIT -> {
                if (fromBank == Bank.LEFT) {
                    "Take Rabbit to the Right Bank. This is safe because Dog and Cabbage don't fight!"
                } else {
                    "Bring Rabbit BACK to the Left Bank to avoid leaving it with the Dog or Cabbage."
                }
            }
            GameItem.DOG -> {
                if (fromBank == Bank.LEFT) {
                    "Take Dog to the Right Bank. Leaving the Cabbage alone on the Left Bank is safe."
                } else {
                    "Bring Dog back."
                }
            }
            GameItem.FOX -> {
                if (fromBank == Bank.LEFT) {
                    "Take Fox to the Right Bank safely."
                } else {
                    "Bring Fox back."
                }
            }
            GameItem.CABBAGE -> {
                if (fromBank == Bank.LEFT) {
                    "Take Cabbage to the Right Bank. Leaving Dog on the Left Bank is completely safe."
                } else {
                    "Bring Cabbage back."
                }
            }
            GameItem.CORN -> {
                if (fromBank == Bank.LEFT) {
                    "Take Corn to the Right Bank safely."
                } else {
                    "Bring Corn back."
                }
            }
            GameItem.WOLF -> {
                if (fromBank == Bank.LEFT) {
                    "Take Wolf across to the Right Bank safely."
                } else {
                    "Bring Wolf back across to prevent conflicts on the other bank."
                }
            }
            GameItem.SHEEP -> {
                if (fromBank == Bank.LEFT) {
                    "Take Sheep to the Right Bank safely."
                } else {
                    "Bring Sheep back across to keep it away from predators."
                }
            }
            GameItem.HAY -> {
                if (fromBank == Bank.LEFT) {
                    "Take Hay across to the Right Bank."
                } else {
                    "Bring Hay back across safely."
                }
            }
            GameItem.LION -> {
                if (fromBank == Bank.LEFT) {
                    "Transport the mighty Lion across to the Right Bank."
                } else {
                    "Row the Lion back across to safeguard the animals."
                }
            }
            GameItem.BEAR -> {
                if (fromBank == Bank.LEFT) {
                    "Transport the Bear across to the Right Bank safely."
                } else {
                    "Row the Bear back across to prevent conflicts on the other bank."
                }
            }
            GameItem.BERRIES -> {
                if (fromBank == Bank.LEFT) {
                    "Take the Berries across to the Right Bank."
                } else {
                    "Bring the Berries back across safely."
                }
            }
            null -> {
                "Farmer rows alone back to the other bank."
            }
            else -> {
                if (fromBank == Bank.LEFT) {
                    "Transport the ${item.displayName} across to the Right Bank safely."
                } else {
                    "Bring the ${item.displayName} back across safely."
                }
            }
        }
    }
}
