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
    ),
    CAT(
        id = "cat",
        displayName = "Cat",
        emoji = "🐱",
        description = "Clever feline that catches Fish & Mice, but clashes with Dogs!",
        dangerTag = "Agile Hunter",
        drawableRes = R.drawable.img_cat
    ),
    FISH(
        id = "fish",
        displayName = "Fish",
        emoji = "🐟",
        description = "Fresh river catch that Cats, Bears & Crocodiles will greedily devour!",
        dangerTag = "River Catch",
        drawableRes = R.drawable.img_fish
    ),
    MOUSE(
        id = "mouse",
        displayName = "Mouse",
        emoji = "🐭",
        description = "Tiny rodent that nibbles Cheese and Grain, but hunted by Cats & Foxes!",
        dangerTag = "Tiny Forager",
        drawableRes = R.drawable.img_mouse
    ),
    CHEESE(
        id = "cheese",
        displayName = "Cheese",
        emoji = "🧀",
        description = "Fragrant golden dairy wheel that Mice will feast upon if left alone!",
        dangerTag = "Aromatic Dairy",
        drawableRes = R.drawable.img_cheese
    ),
    CROCODILE(
        id = "crocodile",
        displayName = "Crocodile",
        emoji = "🐊",
        description = "Armored river predator that attacks Goats, Sheep & Fish if left unattended!",
        dangerTag = "River Stalker",
        drawableRes = R.drawable.img_crocodile
    ),
    GOAT(
        id = "goat",
        displayName = "Goat",
        emoji = "🐐",
        description = "Mountain grazer that eats Carrots, Hay & Cabbage, but prey to Wolves & Tigers!",
        dangerTag = "Mountain Grazer",
        drawableRes = R.drawable.img_goat
    ),
    CARROT(
        id = "carrot",
        displayName = "Carrot",
        emoji = "🥕",
        description = "Sweet crisp garden root that Rabbits & Goats will devour if left alone!",
        dangerTag = "Crisp Crop",
        drawableRes = R.drawable.img_carrot
    ),
    CHICKEN(
        id = "chicken",
        displayName = "Chicken",
        emoji = "🐔",
        description = "Alert farm fowl that pecks at Grain and Corn, but prey to Foxes & Wolves!",
        dangerTag = "Farm Fowl",
        drawableRes = R.drawable.img_chicken
    ),
    GRAIN(
        id = "grain",
        displayName = "Grain",
        emoji = "🌾",
        description = "Sack of farm seeds that Chickens and Mice will peck clean if left alone!",
        dangerTag = "Farm Seed",
        drawableRes = R.drawable.img_grain
    ),
    TIGER(
        id = "tiger",
        displayName = "Tiger",
        emoji = "🐅",
        description = "Ferocious apex predator that hunts Goats, Sheep & Chickens if left without Farmer!",
        dangerTag = "Apex Stalker",
        drawableRes = R.drawable.img_tiger
    ),
    MONKEY(
        id = "monkey",
        displayName = "Monkey",
        emoji = "🐒",
        description = "Nimble jungle acrobat that grabs Bananas & Apples, but terrified of Snakes!",
        dangerTag = "Agile Primate",
        drawableRes = R.drawable.img_monkey
    ),
    BANANA(
        id = "banana",
        displayName = "Banana",
        emoji = "🍌",
        description = "Sweet golden fruit bunch that Monkeys & Bears will greedily devour if left alone!",
        dangerTag = "Tropical Fruit",
        drawableRes = R.drawable.img_banana
    ),
    SNAKE(
        id = "snake",
        displayName = "Snake",
        emoji = "🐍",
        description = "Stealthy venomous serpent that strikes at Frogs, Mice & Chickens!",
        dangerTag = "Venomous Stalker",
        drawableRes = R.drawable.img_snake
    ),
    FROG(
        id = "frog",
        displayName = "Frog",
        emoji = "🐸",
        description = "Nimble wetland hopper that catches Dragonflies & Insects, but prey to Snakes & Eagles!",
        dangerTag = "Marsh Hopper",
        drawableRes = R.drawable.img_frog
    ),
    PANDA(
        id = "panda",
        displayName = "Panda",
        emoji = "🐼",
        description = "Gentle bamboo bear that munches Bamboo shoots, but vulnerable to Tigers!",
        dangerTag = "Gentle Giant",
        drawableRes = R.drawable.img_panda
    ),
    BAMBOO(
        id = "bamboo",
        displayName = "Bamboo",
        emoji = "🎋",
        description = "Fresh crisp bamboo stalks that Pandas and Goats feast upon if left alone!",
        dangerTag = "Crisp Shoots",
        drawableRes = R.drawable.img_bamboo
    ),
    EAGLE(
        id = "eagle",
        displayName = "Eagle",
        emoji = "🦅",
        description = "Soaring raptor that swoops down on Snakes, Frogs, Fish & Monkeys!",
        dangerTag = "Aerial Apex",
        drawableRes = R.drawable.img_eagle
    ),
    HONEY(
        id = "honey",
        displayName = "Honey",
        emoji = "🍯",
        description = "Pot of sweet golden forest nectar that Bears and Monkeys will gobble up!",
        dangerTag = "Golden Nectar",
        drawableRes = R.drawable.img_honey
    ),
    HORSE(
        id = "horse",
        displayName = "Horse",
        emoji = "🐎",
        description = "Noble mountain steed that loves Apples & Hay, but spooked by Wolves & Tigers!",
        dangerTag = "Majestic Steed",
        drawableRes = R.drawable.img_horse
    ),
    APPLE(
        id = "apple",
        displayName = "Apple",
        emoji = "🍎",
        description = "Crisp sweet orchard apple that Horses, Rabbits & Monkeys will devour!",
        dangerTag = "Crisp Orchard Fruit",
        drawableRes = R.drawable.img_apple
    ),
    DRAGONFLY(
        id = "dragonfly",
        displayName = "Dragonfly",
        emoji = "🦗",
        description = "Iridescent marsh insect that Frogs and Chickens will greedily snatch!",
        dangerTag = "Marsh Insect",
        drawableRes = R.drawable.img_dragonfly
    ),
    PENGUIN(
        id = "penguin",
        displayName = "Penguin",
        emoji = "🐧",
        description = "Aquatic polar swimmer that feasts on Fish, but hunted by Bears & Crocodiles!",
        dangerTag = "Polar Swimmer",
        drawableRes = R.drawable.img_penguin
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
        val hasCat = GameItem.CAT in passengers
        val hasFish = GameItem.FISH in passengers
        val hasMouse = GameItem.MOUSE in passengers
        val hasCheese = GameItem.CHEESE in passengers
        val hasCrocodile = GameItem.CROCODILE in passengers
        val hasGoat = GameItem.GOAT in passengers
        val hasCarrot = GameItem.CARROT in passengers
        val hasChicken = GameItem.CHICKEN in passengers
        val hasGrain = GameItem.GRAIN in passengers
        val hasTiger = GameItem.TIGER in passengers

        return when {
            hasTiger && (hasGoat || hasSheep || hasChicken || hasWolf || hasDog) ->
                "⚠️ Restricted Cargo: Tiger will pounce on passengers inside the boat!"
            hasCrocodile && (hasGoat || hasSheep || hasFish || hasChicken) ->
                "⚠️ Restricted Cargo: Crocodile will snap at cargo inside the boat!"
            hasCat && (hasFish || hasMouse) ->
                "⚠️ Restricted Cargo: Cat will hunt prey inside the tight boat!"
            hasDog && hasCat ->
                "⚠️ Restricted Cargo: Dog and Cat will clash violently inside the boat!"
            hasMouse && (hasCheese || hasGrain) ->
                "⚠️ Restricted Cargo: Mouse will nibble the rations inside the boat!"
            hasGoat && (hasCarrot || hasHay || hasCabbage) ->
                "⚠️ Restricted Cargo: Goat will munch crops during transit!"
            hasChicken && (hasGrain || hasCorn) ->
                "⚠️ Restricted Cargo: Chicken will peck feed during transit!"
            hasLion && (hasWolf || hasDog || hasSheep || hasBear) ->
                "⚠️ Restricted Cargo: Lion will attack other passengers inside the small boat!"
            hasBear && (hasWolf || hasDog || hasSheep || hasBerries || hasFish) ->
                "⚠️ Restricted Cargo: Bear will fight or devour cargo inside the boat!"
            hasWolf && (hasDog || hasFox || hasSheep || hasRabbit || hasGoat) ->
                "⚠️ Restricted Cargo: Wolf will fight or attack cargo inside the boat!"
            hasDog && hasFox ->
                "⚠️ Restricted Cargo: Dog and Fox will fight violently inside the small boat!"
            hasDog && (hasRabbit || hasSheep) ->
                "⚠️ Restricted Cargo: Dog and prey cannot share the boat without commotion!"
            hasFox && (hasRabbit || hasBerries || hasChicken) ->
                "⚠️ Restricted Cargo: Fox will attack prey or scatter food inside the rowboat!"
            hasSheep && (hasHay || hasCabbage || hasCorn) ->
                "⚠️ Restricted Cargo: Sheep will devour the feed during transit!"
            hasRabbit && (hasCabbage || hasCorn || hasHay || hasBerries || hasCarrot) ->
                "⚠️ Restricted Cargo: Rabbit will nibble the crops during transit!"
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

    // Level 51: Bamboo Grove
    val LEVEL_51 = PuzzleScenario(
        id = "level_51_bamboo_grove",
        levelNumber = 51,
        title = "Level 51: Bamboo Grove",
        subtitle = "Feline Stalker & River Catch",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.CAT, GameItem.FISH),
        boatCapacity = 1,
        optimalMoves = 3,
        targetTimeSeconds = 40L,
        dangerRules = listOf(
            DangerRule(GameItem.CAT, GameItem.FISH, "Cat Caught The Fish!", "You left the hungry Cat and Fish alone together on the {bank}.", "🐱💥🐟")
        ),
        description = "Transport the agile Cat and fresh river Fish across the bamboo stream without incident.",
        rules = listOf("Boat carries 1 item at a time.", "Keep Cat and Fish separated when unattended.")
    )

    // Level 52: Golden Savannah Dunes
    val LEVEL_52 = PuzzleScenario(
        id = "level_52_savannah_dunes",
        levelNumber = 52,
        title = "Level 52: Golden Savannah Dunes",
        subtitle = "Cat, Mouse & Dairy Wheel",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.CAT, GameItem.MOUSE, GameItem.CHEESE),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Hunted The Mouse!", "You left Cat and Mouse alone together on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Devoured The Cheese!", "You left Mouse and Cheese alone together on the {bank}.", "🐭💥🧀")
        ),
        description = "A timeless battle of wits between Cat, Mouse, and fragrant Swiss Cheese.",
        rules = listOf("Boat carries 1 item at a time.", "Never leave Cat with Mouse, or Mouse with Cheese unattended.")
    )

    // Level 53: Coral Archipelago
    val LEVEL_53 = PuzzleScenario(
        id = "level_53_coral_archipelago",
        levelNumber = 53,
        title = "Level 53: Coral Archipelago",
        subtitle = "Fox, Chicken & Golden Grain",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.FOX, GameItem.CHICKEN, GameItem.GRAIN),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.FOX, GameItem.CHICKEN, "Fox Caught The Chicken!", "You left Fox and Chicken alone together on the {bank}.", "🦊💥🐔"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain alone together on the {bank}.", "🐔💥🌾")
        ),
        description = "The classic farmyard trio across tropical crystalline coral waters.",
        rules = listOf("Rowboat holds 1 item.", "Fox eats Chicken, Chicken pecks Grain.")
    )

    // Level 54: Volcanic Caldera
    val LEVEL_54 = PuzzleScenario(
        id = "level_54_volcanic_caldera",
        levelNumber = 54,
        title = "Level 54: Volcanic Caldera",
        subtitle = "Mountain Grazer & Wild Wolf",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.WOLF, GameItem.GOAT, GameItem.CARROT),
        boatCapacity = 1,
        optimalMoves = 7,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.GOAT, "Wolf Attacked The Goat!", "You left Wolf and Goat alone together on the {bank}.", "🐺💥🐐"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrots!", "You left Goat and Carrot alone together on the {bank}.", "🐐💥🥕")
        ),
        description = "Navigate volcanic magma shores transporting the hungry Goat, crunchy Carrot, and stealthy Wolf.",
        rules = listOf("Boat carries 1 item at a time.", "Safeguard Goat from Wolf, and Carrot from Goat.")
    )

    // Level 55: Cherry Blossom Pagoda
    val LEVEL_55 = PuzzleScenario(
        id = "level_55_cherry_blossom",
        levelNumber = 55,
        title = "Level 55: Cherry Blossom Pagoda",
        subtitle = "Canine, Feline, Rodent & Dairy",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.DOG, GameItem.CAT, GameItem.MOUSE, GameItem.CHEESE),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.CAT, "Dog Chased The Cat!", "You left Dog and Cat alone together on the {bank}.", "🐕💥🐱"),
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Hunted The Mouse!", "You left Cat and Mouse alone together on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Nibbled The Cheese!", "You left Mouse and Cheese alone together on the {bank}.", "🐭💥🧀")
        ),
        description = "A dual-capacity raft crossing four classic domestic animals under sakura petals.",
        rules = listOf("Medium Skiff carries up to 2 items.", "Balance the chain: Dog > Cat > Mouse > Cheese.")
    )

    // Level 56: Deep Rainforest Sanctuary
    val LEVEL_56 = PuzzleScenario(
        id = "level_56_deep_rainforest",
        levelNumber = 56,
        title = "Level 56: Deep Rainforest Sanctuary",
        subtitle = "Jungle Tiger & Mountain Forager",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.TIGER, GameItem.GOAT, GameItem.CARROT, GameItem.GRAIN),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Pounced On The Goat!", "You left Tiger and Goat alone together on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrots!", "You left Goat and Carrot alone together on the {bank}.", "🐐💥🥕"),
            DangerRule(GameItem.GOAT, GameItem.GRAIN, "Goat Feasted On The Grain!", "You left Goat and Grain alone together on the {bank}.", "🐐💥🌾")
        ),
        description = "Deep in the humid rainforest, the mighty Tiger stalks the Goat while crops need saving.",
        rules = listOf("Skiff holds 2 items.", "Tiger preys on Goat; Goat devours Carrots and Grain.")
    )

    // Level 57: Glacial Fjord
    val LEVEL_57 = PuzzleScenario(
        id = "level_57_glacial_fjord",
        levelNumber = 57,
        title = "Level 57: Glacial Fjord",
        subtitle = "Armored Reptile & Grazing Flock",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.CROCODILE, GameItem.SHEEP, GameItem.HAY, GameItem.FISH),
        boatCapacity = 2,
        optimalMoves = 3,
        targetTimeSeconds = 50L,
        dangerRules = listOf(
            DangerRule(GameItem.CROCODILE, GameItem.SHEEP, "Crocodile Snapped At The Sheep!", "You left Crocodile and Sheep alone together on the {bank}.", "🐊💥🐑"),
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Devoured The Fish!", "You left Crocodile and Fish alone together on the {bank}.", "🐊💥🐟"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay alone together on the {bank}.", "🐑💥🌾")
        ),
        description = "Frozen glacial torrents where the cold-water Crocodile lurks for Sheep and Fish.",
        rules = listOf("Skiff holds 2 items.", "Isolate the Crocodile from docile prey and river catch.")
    )

    // Level 58: Twilight Redwood Valley
    val LEVEL_58 = PuzzleScenario(
        id = "level_58_redwood_valley",
        levelNumber = 58,
        title = "Level 58: Twilight Redwood Valley",
        subtitle = "Forest Bear, Catch & Foragers",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.BEAR, GameItem.FISH, GameItem.BERRIES, GameItem.RABBIT, GameItem.CARROT),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 70L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.FISH, "Bear Gobbled The Fish!", "You left Bear and Fish alone together on the {bank}.", "🐻💥🐟"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries alone together on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.BERRIES, "Rabbit Nibbled The Berries!", "You left Rabbit and Berries alone together on the {bank}.", "🐇💥🫐"),
            DangerRule(GameItem.RABBIT, GameItem.CARROT, "Rabbit Munched The Carrot!", "You left Rabbit and Carrot alone together on the {bank}.", "🐇💥🥕")
        ),
        description = "Under ancient timber, the Bear hungers for Fish and Berries while the Rabbit seeks treats.",
        rules = listOf("Skiff carries 2 items.", "Prevent the woodland banquet across 7 optimal steps.")
    )

    // Level 59: Bioluminescent Cavern
    val LEVEL_59 = PuzzleScenario(
        id = "level_59_bioluminescent_cavern",
        levelNumber = 59,
        title = "Level 59: Bioluminescent Cavern",
        subtitle = "Subterranean Double Caravan",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.CAT, GameItem.MOUSE, GameItem.CHEESE, GameItem.DOG, GameItem.RABBIT, GameItem.CARROT),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.RABBIT, "Dog Chased The Rabbit!", "You left Dog and Rabbit alone together on the {bank}.", "🐕💥🐇"),
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Hunted The Mouse!", "You left Cat and Mouse alone together on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Nibbled The Cheese!", "You left Mouse and Cheese alone together on the {bank}.", "🐭💥🧀"),
            DangerRule(GameItem.RABBIT, GameItem.CARROT, "Rabbit Munched The Carrot!", "You left Rabbit and Carrot alone together on the {bank}.", "🐇💥🥕")
        ),
        description = "Two parallel predator-prey chains traversing an azure crystal cavern.",
        rules = listOf("Skiff carries up to 2 items.", "Manage parallel chains simultaneously.")
    )

    // Level 60: Sunken Atlantis Ruins
    val LEVEL_60 = PuzzleScenario(
        id = "level_60_atlantis_ruins",
        levelNumber = 60,
        title = "Level 60: Sunken Atlantis Ruins",
        subtitle = "Double Homestead Expedition",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.WOLF, GameItem.SHEEP, GameItem.HAY, GameItem.FOX, GameItem.CHICKEN, GameItem.GRAIN),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep alone together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay alone together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.FOX, GameItem.CHICKEN, "Fox Caught The Chicken!", "You left Fox and Chicken alone together on the {bank}.", "🦊💥🐔"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain alone together on the {bank}.", "🐔💥🌾")
        ),
        description = "Wolf-Sheep-Hay meets Fox-Chicken-Grain in flooded ancient palace ruins.",
        rules = listOf("Skiff holds 2 items.", "Coordinate 2 legendary trios without leaving prey unattended.")
    )

    // Level 61: Highland Heather Moors
    val LEVEL_61 = PuzzleScenario(
        id = "level_61_heather_moors",
        levelNumber = 61,
        title = "Level 61: Highland Heather Moors",
        subtitle = "Apex Rivals & Highland Herd",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.TIGER, GameItem.CROCODILE, GameItem.GOAT, GameItem.CARROT, GameItem.FISH),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.CROCODILE, "Tiger Clashed With Crocodile!", "You left Tiger and Crocodile together on the {bank}.", "🐅💥🐊"),
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Hunted The Goat!", "You left Tiger and Goat together on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.CROCODILE, GameItem.GOAT, "Crocodile Snapped At Goat!", "You left Crocodile and Goat together on the {bank}.", "🐊💥🐐"),
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Devoured The Fish!", "You left Crocodile and Fish together on the {bank}.", "🐊💥🐟"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrot!", "You left Goat and Carrot together on the {bank}.", "🐐💥🥕")
        ),
        description = "Misty loch where Tiger and Crocodile both covet the Goat while crops and fish must pass.",
        rules = listOf("Skiff carries 2 items.", "Carefully orchestrate 5 danger rules.")
    )

    // Level 62: Autumn Maple Gorge
    val LEVEL_62 = PuzzleScenario(
        id = "level_62_maple_gorge",
        levelNumber = 62,
        title = "Level 62: Autumn Maple Gorge",
        subtitle = "Granary Web of Foes",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.FOX, GameItem.CHICKEN, GameItem.GRAIN, GameItem.MOUSE, GameItem.CHEESE),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.FOX, GameItem.CHICKEN, "Fox Caught The Chicken!", "You left Fox and Chicken together on the {bank}.", "🦊💥🐔"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain together on the {bank}.", "🐔💥🌾"),
            DangerRule(GameItem.CHICKEN, GameItem.MOUSE, "Chicken Pecked The Mouse!", "You left Chicken and Mouse together on the {bank}.", "🐔💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.GRAIN, "Mouse Nibbled The Grain!", "You left Mouse and Grain together on the {bank}.", "🐭💥🌾"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Devoured The Cheese!", "You left Mouse and Cheese together on the {bank}.", "🐭💥🧀")
        ),
        description = "Golden autumn gorge where granary pests and fowl create a web of dietary rivalries.",
        rules = listOf("Skiff holds 2 items.", "Unravel the interlocking 5-rule harvest puzzle.")
    )

    // Level 63: Emerald Rice Terraces
    val LEVEL_63 = PuzzleScenario(
        id = "level_63_rice_terraces",
        levelNumber = 63,
        title = "Level 63: Emerald Rice Terraces",
        subtitle = "The Domestic Chain",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.DOG, GameItem.CAT, GameItem.FISH, GameItem.MOUSE, GameItem.CHEESE, GameItem.GRAIN),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.DOG, GameItem.CAT, "Dog Chased The Cat!", "You left Dog and Cat together on the {bank}.", "🐕💥🐱"),
            DangerRule(GameItem.CAT, GameItem.FISH, "Cat Ate The Fish!", "You left Cat and Fish together on the {bank}.", "🐱💥🐟"),
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Hunted The Mouse!", "You left Cat and Mouse together on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Ate The Cheese!", "You left Mouse and Cheese together on the {bank}.", "🐭💥🧀"),
            DangerRule(GameItem.MOUSE, GameItem.GRAIN, "Mouse Nibbled The Grain!", "You left Mouse and Grain together on the {bank}.", "🐭💥🌾")
        ),
        description = "Cascading rice terraces where Dog, Cat, Mouse, Fish, Cheese, and Grain must find balance.",
        rules = listOf("Skiff carries 2 items.", "Cross all 6 domestic elements safely.")
    )

    // Level 64: Dragon's Tooth Peak
    val LEVEL_64 = PuzzleScenario(
        id = "level_64_dragon_peak",
        levelNumber = 64,
        title = "Level 64: Dragon's Tooth Peak",
        subtitle = "Alpine Barge Convoy",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.TIGER, GameItem.WOLF, GameItem.SHEEP, GameItem.GOAT, GameItem.HAY, GameItem.CARROT),
        boatCapacity = 3,
        optimalMoves = 3,
        targetTimeSeconds = 60L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Hunted The Goat!", "You left Tiger and Goat together on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrot!", "You left Goat and Carrot together on the {bank}.", "🐐💥🥕"),
            DangerRule(GameItem.TIGER, GameItem.WOLF, "Tiger Fought The Wolf!", "You left Tiger and Wolf together on the {bank}.", "🐅💥🐺")
        ),
        description = "High mountain torrents where an alpine barge transports two fierce predators and their prey.",
        rules = listOf("Large Barge carries up to 3 items.", "Deliver all six mountain travelers in 3 optimal moves.")
    )

    // Level 65: Whispering Mangrove Swamp
    val LEVEL_65 = PuzzleScenario(
        id = "level_65_mangrove_swamp",
        levelNumber = 65,
        title = "Level 65: Whispering Mangrove Swamp",
        subtitle = "Swamp Giants & Meadow Grazer",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.CROCODILE, GameItem.BEAR, GameItem.FISH, GameItem.GOAT, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 65L,
        dangerRules = listOf(
            DangerRule(GameItem.CROCODILE, GameItem.GOAT, "Crocodile Snapped At Goat!", "You left Crocodile and Goat together on the {bank}.", "🐊💥🐐"),
            DangerRule(GameItem.BEAR, GameItem.FISH, "Bear Caught The Fish!", "You left Bear and Fish together on the {bank}.", "🐻💥🐟"),
            DangerRule(GameItem.GOAT, GameItem.HAY, "Goat Ate The Hay!", "You left Goat and Hay together on the {bank}.", "🐐💥🌾")
        ),
        description = "Twisting mangrove channels sheltering the apex Crocodile and greedy Bear.",
        rules = listOf("Skiff holds 2 items.", "Keep Goat away from Crocodile, Fish from Bear, and Hay from Goat.")
    )

    // Level 66: Moonlit Willow Haven
    val LEVEL_66 = PuzzleScenario(
        id = "level_66_moonlit_willow",
        levelNumber = 66,
        title = "Level 66: Moonlit Willow Haven",
        subtitle = "Midnight Barnyard Shadows",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.CAT, GameItem.MOUSE, GameItem.CHEESE, GameItem.CHICKEN, GameItem.GRAIN, GameItem.FOX),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Hunted The Mouse!", "You left Cat and Mouse together on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Ate The Cheese!", "You left Mouse and Cheese together on the {bank}.", "🐭💥🧀"),
            DangerRule(GameItem.FOX, GameItem.CHICKEN, "Fox Caught The Chicken!", "You left Fox and Chicken together on the {bank}.", "🦊💥🐔"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain together on the {bank}.", "🐔💥🌾"),
            DangerRule(GameItem.FOX, GameItem.MOUSE, "Fox Snapped Up The Mouse!", "You left Fox and Mouse together on the {bank}.", "🦊💥🐭")
        ),
        description = "Under weeping willow branches at midnight, clever predators and prey navigate the stream.",
        rules = listOf("Skiff carries 2 items.", "Solve the 5 cross-species nocturnal danger rules.")
    )

    // Level 67: Golden Sun Temple
    val LEVEL_67 = PuzzleScenario(
        id = "level_67_sun_temple",
        levelNumber = 67,
        title = "Level 67: Golden Sun Temple",
        subtitle = "Monarchs of Savannah & Jungle",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.TIGER, GameItem.GOAT, GameItem.SHEEP, GameItem.CARROT, GameItem.HAY),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 85L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.TIGER, "Lion Clashed With Tiger!", "You left Lion and Tiger together on the {bank}.", "🦁💥🐅"),
            DangerRule(GameItem.LION, GameItem.GOAT, "Lion Attacked The Goat!", "You left Lion and Goat together on the {bank}.", "🦁💥🐐"),
            DangerRule(GameItem.LION, GameItem.SHEEP, "Lion Attacked The Sheep!", "You left Lion and Sheep together on the {bank}.", "🦁💥🐑"),
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Hunted The Goat!", "You left Tiger and Goat together on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.TIGER, GameItem.SHEEP, "Tiger Hunted The Sheep!", "You left Tiger and Sheep together on the {bank}.", "🐅💥🐑"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrot!", "You left Goat and Carrot together on the {bank}.", "🐐💥🥕"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay together on the {bank}.", "🐑💥🌾")
        ),
        description = "Sun-gilded temple waters where Lion and Tiger compete over flocks and crops.",
        rules = listOf("Imperial Barge holds up to 3 items.", "Balance 7 danger rules between two apex kings.")
    )

    // Level 68: Crystal Geode Grotto
    val LEVEL_68 = PuzzleScenario(
        id = "level_68_geode_grotto",
        levelNumber = 68,
        title = "Level 68: Crystal Geode Grotto",
        subtitle = "Triumvirate of Prismatic Predators",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.CROCODILE, GameItem.TIGER, GameItem.BEAR, GameItem.FISH, GameItem.GOAT, GameItem.CARROT),
        boatCapacity = 3,
        optimalMoves = 5,
        targetTimeSeconds = 85L,
        dangerRules = listOf(
            DangerRule(GameItem.CROCODILE, GameItem.TIGER, "Crocodile Fought The Tiger!", "You left Crocodile and Tiger together on the {bank}.", "🐊💥🐅"),
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Hunted The Goat!", "You left Tiger and Goat together on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.CROCODILE, GameItem.GOAT, "Crocodile Snapped At Goat!", "You left Crocodile and Goat together on the {bank}.", "🐊💥🐐"),
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Devoured The Fish!", "You left Crocodile and Fish together on the {bank}.", "🐊💥🐟"),
            DangerRule(GameItem.BEAR, GameItem.FISH, "Bear Caught The Fish!", "You left Bear and Fish together on the {bank}.", "🐻💥🐟"),
            DangerRule(GameItem.GOAT, GameItem.CARROT, "Goat Munched The Carrot!", "You left Goat and Carrot together on the {bank}.", "🐐💥🥕")
        ),
        description = "Three mighty apex predators—Crocodile, Tiger, and Bear—must be ferried without bloodshed.",
        rules = listOf("Imperial Barge holds up to 3 items.", "Isolate the triple-predator rivalries.")
    )

    // Level 69: Starfall Astral Canyon
    val LEVEL_69 = PuzzleScenario(
        id = "level_69_starfall_canyon",
        levelNumber = 69,
        title = "Level 69: Starfall Astral Canyon",
        subtitle = "The Starlight Menagerie",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(
            GameItem.TIGER,
            GameItem.CROCODILE,
            GameItem.WOLF,
            GameItem.FOX,
            GameItem.DOG,
            GameItem.GOAT,
            GameItem.SHEEP,
            GameItem.CHICKEN
        ),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 110L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.CROCODILE, "Tiger Clashed With Crocodile!", "You left Tiger and Crocodile together on the {bank}.", "🐅💥🐊"),
            DangerRule(GameItem.TIGER, GameItem.WOLF, "Tiger Fought The Wolf!", "You left Tiger and Wolf together on the {bank}.", "🐅💥🐺"),
            DangerRule(GameItem.CROCODILE, GameItem.GOAT, "Crocodile Attacked The Goat!", "You left Crocodile and Goat together on the {bank}.", "🐊💥🐐"),
            DangerRule(GameItem.CROCODILE, GameItem.SHEEP, "Crocodile Snapped At Sheep!", "You left Crocodile and Sheep together on the {bank}.", "🐊💥🐑"),
            DangerRule(GameItem.WOLF, GameItem.GOAT, "Wolf Attacked The Goat!", "You left Wolf and Goat together on the {bank}.", "🐺💥🐐"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep together on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.FOX, GameItem.CHICKEN, "Fox Caught The Chicken!", "You left Fox and Chicken together on the {bank}.", "🦊💥🐔"),
            DangerRule(GameItem.DOG, GameItem.FOX, "Dog Clashed With Fox!", "You left Dog and Fox together on the {bank}.", "🐕💥🦊")
        ),
        description = "Eight animal companions beneath shooting stars in a symphony of predator and herd coordination.",
        rules = listOf("Barge holds 3 items.", "8 danger rules across 8 distinct animal characters.")
    )

    // Level 70: The Grand Emperor's Elysium
    val LEVEL_70 = PuzzleScenario(
        id = "level_70_emperors_elysium",
        levelNumber = 70,
        title = "Level 70: The Grand Emperor's Elysium",
        subtitle = "The Supreme Pinnacle of River Crossing",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(
            GameItem.TIGER,
            GameItem.CROCODILE,
            GameItem.BEAR,
            GameItem.DOG,
            GameItem.GOAT,
            GameItem.FISH,
            GameItem.CARROT,
            GameItem.GRAIN
        ),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 120L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.CROCODILE, "Tiger Clashed With The Crocodile!", "You left Tiger and Crocodile together on the {bank}.", "🐅💥🐊"),
            DangerRule(GameItem.CROCODILE, GameItem.BEAR, "Crocodile Fought The Bear!", "You left Crocodile and Bear together on the {bank}.", "🐊💥🐻"),
            DangerRule(GameItem.BEAR, GameItem.DOG, "Bear Clashed With The Dog!", "You left Bear and Dog together on the {bank}.", "🐻💥🐕"),
            DangerRule(GameItem.DOG, GameItem.GOAT, "Dog Chased The Goat!", "You left Dog and Goat together on the {bank}.", "🐕💥🐐"),
            DangerRule(GameItem.BEAR, GameItem.FISH, "Bear Devoured The Fish!", "You left Bear and Fish together on the {bank}.", "🐻💥🐟"),
            DangerRule(GameItem.CROCODILE, GameItem.CARROT, "Crocodile Smashed The Carrots!", "You left Crocodile and Carrots together on the {bank}.", "🐊💥🥕"),
            DangerRule(GameItem.FISH, GameItem.GRAIN, "Fish Nibbled The Grain!", "You left Fish and Grain together on the {bank}.", "🐟💥🌾")
        ),
        description = "The ultimate 70-level milestone! 8 magnificent creatures in the supreme pinnacle of river crossing.",
        rules = listOf(
            "Large Imperial Barge carries up to 3 items.",
            "Orchestrate 7 danger rules to conquer Level 70 and claim the Imperial Grandmaster Crown!"
        )
    )

    // Level 71: Monkey Canopy Crossing
    val LEVEL_71 = PuzzleScenario(
        id = "level_71_monkey_canopy",
        levelNumber = 71,
        title = "Level 71: Monkey Canopy",
        subtitle = "Monkey, Banana, Snake, and Frog",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.MONKEY, GameItem.BANANA, GameItem.SNAKE, GameItem.FROG),
        boatCapacity = 2,
        optimalMoves = 5,
        targetTimeSeconds = 55L,
        dangerRules = listOf(
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Devoured The Bananas!", "You left Monkey and Bananas alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Struck The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.SNAKE, GameItem.MONKEY, "Snake Bit The Monkey!", "You left Snake and Monkey alone on the {bank}.", "🐍💥🐒")
        ),
        description = "Navigate a vibrant rainforest river with playful monkeys and stealthy serpents.",
        rules = listOf(
            "Twin-seat canoe carries Farmer + up to 2 items.",
            "Monkey eats Banana if unattended.",
            "Snake attacks Frog and frightens Monkey."
        )
    )

    // Level 72: Panda Bamboo Sanctuary
    val LEVEL_72 = PuzzleScenario(
        id = "level_72_panda_sanctuary",
        levelNumber = 72,
        title = "Level 72: Panda Sanctuary",
        subtitle = "Panda, Bamboo, Tiger, Chicken, and Grain",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.PANDA, GameItem.BAMBOO, GameItem.TIGER, GameItem.CHICKEN, GameItem.GRAIN),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 70L,
        dangerRules = listOf(
            DangerRule(GameItem.PANDA, GameItem.BAMBOO, "Panda Munched The Bamboo!", "You left Panda and Bamboo alone on the {bank}.", "🐼💥🎋"),
            DangerRule(GameItem.TIGER, GameItem.PANDA, "Tiger Attacked The Panda!", "You left Tiger and Panda alone on the {bank}.", "🐅💥🐼"),
            DangerRule(GameItem.TIGER, GameItem.CHICKEN, "Tiger Pounced On The Chicken!", "You left Tiger and Chicken alone on the {bank}.", "🐅💥🐔"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain alone on the {bank}.", "🐔💥🌾")
        ),
        description = "A peaceful misty bamboo stream guarded by an alert Bengal tiger.",
        rules = listOf(
            "Canoe holds up to 2 items.",
            "Protect Panda's bamboo and keep Chicken safe from Tiger."
        )
    )

    // Level 73: Highland Steed Pastures
    val LEVEL_73 = PuzzleScenario(
        id = "level_73_highland_steed",
        levelNumber = 73,
        title = "Level 73: Highland Pastures",
        subtitle = "Horse, Apple, Wolf, Sheep, and Hay",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.HORSE, GameItem.APPLE, GameItem.WOLF, GameItem.SHEEP, GameItem.HAY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.HORSE, GameItem.APPLE, "Horse Munched The Apples!", "You left Horse and Apple alone on the {bank}.", "🐎💥🍎"),
            DangerRule(GameItem.HORSE, GameItem.HAY, "Horse Devoured The Hay!", "You left Horse and Hay alone on the {bank}.", "🐎💥🌾"),
            DangerRule(GameItem.WOLF, GameItem.HORSE, "Wolf Spooked The Horse!", "You left Wolf and Horse alone on the {bank}.", "🐺💥🐎"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep alone on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Ate The Hay!", "You left Sheep and Hay alone on the {bank}.", "🐑💥🌾")
        ),
        description = "Purple heather winds blow over lochs as highlanders guide their noble steed.",
        rules = listOf(
            "Canoe holds Farmer + 2 items.",
            "Wolf stalks both Horse and Sheep; Horse & Sheep both crave Hay!"
        )
    )

    // Level 74: Honeycomb Forest
    val LEVEL_74 = PuzzleScenario(
        id = "level_74_honeycomb_glade",
        levelNumber = 74,
        title = "Level 74: Honeycomb Glade",
        subtitle = "Bear, Honey, Monkey, Banana, and Berries",
        difficulty = PuzzleDifficulty.MEDIUM,
        items = listOf(GameItem.BEAR, GameItem.HONEY, GameItem.MONKEY, GameItem.BANANA, GameItem.BERRIES),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 75L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.HONEY, "Bear Gobbled The Honey!", "You left Bear and Honey alone on the {bank}.", "🐻💥🍯"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Devoured The Berries!", "You left Bear and Berries alone on the {bank}.", "🐻💥🫐"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Ate The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.MONKEY, GameItem.HONEY, "Monkey Stole The Honey!", "You left Monkey and Honey alone on the {bank}.", "🐒💥🍯"),
            DangerRule(GameItem.BEAR, GameItem.MONKEY, "Bear Chased The Monkey!", "You left Bear and Monkey alone on the {bank}.", "🐻💥🐒")
        ),
        description = "Forest giants and nimble primates vie for sweet golden delicacies.",
        rules = listOf(
            "Canoe carries 2 items.",
            "Both Bear and Monkey will snatch Honey if given the chance!"
        )
    )

    // Level 75: Emerald Marshland
    val LEVEL_75 = PuzzleScenario(
        id = "level_75_emerald_marsh",
        levelNumber = 75,
        title = "Level 75: Emerald Marshland",
        subtitle = "Frog, Dragonfly, Snake, Crocodile, and Fish",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.FROG, GameItem.DRAGONFLY, GameItem.SNAKE, GameItem.CROCODILE, GameItem.FISH),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Snapped The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Struck The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.CROCODILE, GameItem.SNAKE, "Crocodile Snapped The Snake!", "You left Crocodile and Snake alone on the {bank}.", "🐊💥🐍"),
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Swallowed The Fish!", "You left Crocodile and Fish alone on the {bank}.", "🐊💥🐟")
        ),
        description = "Bioluminescent wetlands teeming with swift predators and hopping amphibians.",
        rules = listOf(
            "Canoe carries 2 items.",
            "A four-tier wetland food web requires strategic planning."
        )
    )

    // Level 76: Raptor's Crag
    val LEVEL_76 = PuzzleScenario(
        id = "level_76_raptors_crag",
        levelNumber = 76,
        title = "Level 76: Raptor's Crag",
        subtitle = "Eagle, Snake, Frog, Fish, and Dragonfly",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.EAGLE, GameItem.SNAKE, GameItem.FROG, GameItem.FISH, GameItem.DRAGONFLY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.EAGLE, GameItem.SNAKE, "Eagle Swooped On The Snake!", "You left Eagle and Snake alone on the {bank}.", "🦅💥🐍"),
            DangerRule(GameItem.EAGLE, GameItem.FISH, "Eagle Snatched The Fish!", "You left Eagle and Fish alone on the {bank}.", "🦅💥🐟"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Struck The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Caught The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗")
        ),
        description = "Alpine canyon heights where golden eagles rule the river gorge from above.",
        rules = listOf(
            "Canoe carries 2 items.",
            "Eagle strikes from the skies at Snake and Fish."
        )
    )

    // Level 77: Polar Glacial Drift
    val LEVEL_77 = PuzzleScenario(
        id = "level_77_polar_drift",
        levelNumber = 77,
        title = "Level 77: Polar Glacial Drift",
        subtitle = "Penguin, Fish, Bear, Berries, and Honey",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.PENGUIN, GameItem.FISH, GameItem.BEAR, GameItem.BERRIES, GameItem.HONEY),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 80L,
        dangerRules = listOf(
            DangerRule(GameItem.PENGUIN, GameItem.FISH, "Penguin Devoured The Fish!", "You left Penguin and Fish alone on the {bank}.", "🐧💥🐟"),
            DangerRule(GameItem.BEAR, GameItem.PENGUIN, "Bear Attacked The Penguin!", "You left Bear and Penguin alone on the {bank}.", "🐻💥🐧"),
            DangerRule(GameItem.BEAR, GameItem.HONEY, "Bear Ate The Honey!", "You left Bear and Honey alone on the {bank}.", "🐻💥🍯"),
            DangerRule(GameItem.BEAR, GameItem.BERRIES, "Bear Gobbled The Berries!", "You left Bear and Berries alone on the {bank}.", "🐻💥🫐")
        ),
        description = "Floating ice floes drift silently through frigid crystalline polar waters.",
        rules = listOf(
            "Reinforced Ice-Barge carries 2 items.",
            "Keep Penguin safe from Bear and Fish safe from Penguin!"
        )
    )

    // Level 78: Golden Oasis Bazaar
    val LEVEL_78 = PuzzleScenario(
        id = "level_78_oasis_bazaar",
        levelNumber = 78,
        title = "Level 78: Oasis Bazaar",
        subtitle = "Monkey, Apple, Banana, Dog, Cheese, and Mouse",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.MONKEY, GameItem.APPLE, GameItem.BANANA, GameItem.DOG, GameItem.CHEESE, GameItem.MOUSE),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 95L,
        dangerRules = listOf(
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Snatched The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.MONKEY, GameItem.APPLE, "Monkey Munched The Apples!", "You left Monkey and Apple alone on the {bank}.", "🐒💥🍎"),
            DangerRule(GameItem.DOG, GameItem.MONKEY, "Dog Barked & Chased Monkey!", "You left Dog and Monkey alone on the {bank}.", "🐕💥🐒"),
            DangerRule(GameItem.DOG, GameItem.MOUSE, "Dog Chased The Mouse!", "You left Dog and Mouse alone on the {bank}.", "🐕💥🐭"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Nibbled The Cheese!", "You left Mouse and Cheese alone on the {bank}.", "🐭💥🧀")
        ),
        description = "Traders gather under date palms by moonlit desert canals.",
        rules = listOf(
            "Desert Skiff holds up to 2 items.",
            "Protect the market's fruit and cheeses from agile foragers."
        )
    )

    // Level 79: Forbidden Serpent Temple
    val LEVEL_79 = PuzzleScenario(
        id = "level_79_serpent_temple",
        levelNumber = 79,
        title = "Level 79: Serpent Temple",
        subtitle = "Snake, Eagle, Chicken, Grain, Frog, and Dragonfly",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.SNAKE, GameItem.EAGLE, GameItem.CHICKEN, GameItem.GRAIN, GameItem.FROG, GameItem.DRAGONFLY),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 95L,
        dangerRules = listOf(
            DangerRule(GameItem.EAGLE, GameItem.SNAKE, "Eagle Swooped On The Snake!", "You left Eagle and Snake alone on the {bank}.", "🦅💥🐍"),
            DangerRule(GameItem.SNAKE, GameItem.CHICKEN, "Snake Struck The Chicken!", "You left Snake and Chicken alone on the {bank}.", "🐍💥🐔"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Devoured The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.CHICKEN, GameItem.GRAIN, "Chicken Pecked The Grain!", "You left Chicken and Grain alone on the {bank}.", "🐔💥🌾"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Caught The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗")
        ),
        description = "Ancient moss-covered pyramid steps overlook an overgrown holy river.",
        rules = listOf(
            "Canoe holds up to 2 items.",
            "Snake threatens both Chicken and Frog; Eagle keeps Snake in check."
        )
    )

    // Level 80: Panda's Moonlit Waterfall
    val LEVEL_80 = PuzzleScenario(
        id = "level_80_moonlit_waterfall",
        levelNumber = 80,
        title = "Level 80: Moonlit Waterfall",
        subtitle = "Panda, Bamboo, Monkey, Banana, Tiger, and Goat",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.PANDA, GameItem.BAMBOO, GameItem.MONKEY, GameItem.BANANA, GameItem.TIGER, GameItem.GOAT),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 100L,
        dangerRules = listOf(
            DangerRule(GameItem.PANDA, GameItem.BAMBOO, "Panda Munched The Bamboo!", "You left Panda and Bamboo alone on the {bank}.", "🐼💥🎋"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Snatched The Banana!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.TIGER, GameItem.PANDA, "Tiger Attacked The Panda!", "You left Tiger and Panda alone on the {bank}.", "🐅💥🐼"),
            DangerRule(GameItem.TIGER, GameItem.GOAT, "Tiger Attacked The Goat!", "You left Tiger and Goat alone on the {bank}.", "🐅💥🐐"),
            DangerRule(GameItem.GOAT, GameItem.BAMBOO, "Goat Nibbled The Bamboo!", "You left Goat and Bamboo alone on the {bank}.", "🐐💥🎋")
        ),
        description = "Cascading twin waterfalls bathe in silvery moonlight as rare beasts cross.",
        rules = listOf(
            "Canoe carries 2 items.",
            "Both Panda and Goat love Bamboo; Tiger stalks both Panda and Goat!"
        )
    )

    // Level 81: Whispering Autumn Orchard
    val LEVEL_81 = PuzzleScenario(
        id = "level_81_autumn_orchard",
        levelNumber = 81,
        title = "Level 81: Autumn Orchard",
        subtitle = "Horse, Apple, Rabbit, Carrot, Fox, and Dog",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.HORSE, GameItem.APPLE, GameItem.RABBIT, GameItem.CARROT, GameItem.FOX, GameItem.DOG),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 100L,
        dangerRules = listOf(
            DangerRule(GameItem.HORSE, GameItem.APPLE, "Horse Devoured The Apples!", "You left Horse and Apple alone on the {bank}.", "🐎💥🍎"),
            DangerRule(GameItem.RABBIT, GameItem.CARROT, "Rabbit Ate The Carrots!", "You left Rabbit and Carrot alone on the {bank}.", "🐇💥🥕"),
            DangerRule(GameItem.RABBIT, GameItem.APPLE, "Rabbit Nibbled The Apples!", "You left Rabbit and Apple alone on the {bank}.", "🐇💥🍎"),
            DangerRule(GameItem.FOX, GameItem.RABBIT, "Fox Hunted The Rabbit!", "You left Fox and Rabbit alone on the {bank}.", "🦊💥🐇"),
            DangerRule(GameItem.DOG, GameItem.FOX, "Dog Clashed With The Fox!", "You left Dog and Fox alone on the {bank}.", "🐕💥🦊")
        ),
        description = "Ripe crimson apples drift upon golden autumn currents by a rustic watermill.",
        rules = listOf(
            "Canoe holds 2 items.",
            "Protect apples and carrots from hungry herbivores while preventing predator brawls."
        )
    )

    // Level 82: Prismatic Geyser Basin
    val LEVEL_82 = PuzzleScenario(
        id = "level_82_geyser_basin",
        levelNumber = 82,
        title = "Level 82: Prismatic Basin",
        subtitle = "Crocodile, Frog, Dragonfly, Fish, Cat, and Mouse",
        difficulty = PuzzleDifficulty.HARD,
        items = listOf(GameItem.CROCODILE, GameItem.FROG, GameItem.DRAGONFLY, GameItem.FISH, GameItem.CAT, GameItem.MOUSE),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 100L,
        dangerRules = listOf(
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Swallowed The Fish!", "You left Crocodile and Fish alone on the {bank}.", "🐊💥🐟"),
            DangerRule(GameItem.CROCODILE, GameItem.CAT, "Crocodile Snatched The Cat!", "You left Crocodile and Cat alone on the {bank}.", "🐊💥🐱"),
            DangerRule(GameItem.CAT, GameItem.FISH, "Cat Clawed The Fish!", "You left Cat and Fish alone on the {bank}.", "🐱💥🐟"),
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Pounced On The Mouse!", "You left Cat and Mouse alone on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Snapped The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗")
        ),
        description = "Rainbow thermal waters steam gently amid volcanic rock formations.",
        rules = listOf(
            "Steamboat holds 2 items.",
            "Crocodile rules the waters; Cat hunts both Fish and Mouse."
        )
    )

    // Level 83: Savannah Twilight Watering Hole
    val LEVEL_83 = PuzzleScenario(
        id = "level_83_savannah_twilight",
        levelNumber = 83,
        title = "Level 83: Savannah Twilight",
        subtitle = "Lion, Monkey, Banana, Horse, Hay, and Eagle",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.LION, GameItem.MONKEY, GameItem.BANANA, GameItem.HORSE, GameItem.HAY, GameItem.EAGLE),
        boatCapacity = 2,
        optimalMoves = 9,
        targetTimeSeconds = 105L,
        dangerRules = listOf(
            DangerRule(GameItem.LION, GameItem.HORSE, "Lion Attacked The Horse!", "You left Lion and Horse alone on the {bank}.", "🦁💥🐎"),
            DangerRule(GameItem.LION, GameItem.MONKEY, "Lion Pounced On The Monkey!", "You left Lion and Monkey alone on the {bank}.", "🦁💥🐒"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Snatched The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.HORSE, GameItem.HAY, "Horse Munched The Hay!", "You left Horse and Hay alone on the {bank}.", "🐎💥🌾"),
            DangerRule(GameItem.EAGLE, GameItem.MONKEY, "Eagle Swooped On The Monkey!", "You left Eagle and Monkey alone on the {bank}.", "🦅💥🐒")
        ),
        description = "Fiery orange horizon casts deep shadows across a tranquil African river bend.",
        rules = listOf(
            "Savannah Barge carries 2 items.",
            "Lion and Eagle both threaten Monkey; Lion hunts the noble Horse."
        )
    )

    // Level 84: Polar Aurora Sanctuary
    val LEVEL_84 = PuzzleScenario(
        id = "level_84_polar_aurora",
        levelNumber = 84,
        title = "Level 84: Aurora Sanctuary",
        subtitle = "Bear, Penguin, Fish, Honey, and Eagle",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.BEAR, GameItem.PENGUIN, GameItem.FISH, GameItem.HONEY, GameItem.EAGLE),
        boatCapacity = 2,
        optimalMoves = 7,
        targetTimeSeconds = 85L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.PENGUIN, "Bear Attacked The Penguin!", "You left Bear and Penguin alone on the {bank}.", "🐻💥🐧"),
            DangerRule(GameItem.BEAR, GameItem.HONEY, "Bear Devoured The Honey!", "You left Bear and Honey alone on the {bank}.", "🐻💥🍯"),
            DangerRule(GameItem.PENGUIN, GameItem.FISH, "Penguin Ate The Fish!", "You left Penguin and Fish alone on the {bank}.", "🐧💥🐟"),
            DangerRule(GameItem.EAGLE, GameItem.FISH, "Eagle Snatched The Fish!", "You left Eagle and Fish alone on the {bank}.", "🦅💥🐟")
        ),
        description = "Shimmering curtains of violet and green aurora dance over glacial waters.",
        rules = listOf(
            "Canoe holds 2 items.",
            "Bear hungers for Penguin and Honey; Eagle and Penguin both target Fish."
        )
    )

    // Level 85: Enchanted Lotus Lagoon
    val LEVEL_85 = PuzzleScenario(
        id = "level_85_lotus_lagoon",
        levelNumber = 85,
        title = "Level 85: Lotus Lagoon",
        subtitle = "Panda, Bamboo, Frog, Dragonfly, Snake, Cat, and Fish",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.PANDA, GameItem.BAMBOO, GameItem.FROG, GameItem.DRAGONFLY, GameItem.SNAKE, GameItem.CAT, GameItem.FISH),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 110L,
        dangerRules = listOf(
            DangerRule(GameItem.PANDA, GameItem.BAMBOO, "Panda Munched The Bamboo!", "You left Panda and Bamboo alone on the {bank}.", "🐼💥🎋"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Caught The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Struck The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.CAT, GameItem.FISH, "Cat Clawed The Fish!", "You left Cat and Fish alone on the {bank}.", "🐱💥🐟"),
            DangerRule(GameItem.SNAKE, GameItem.CAT, "Snake Struck The Cat!", "You left Snake and Cat alone on the {bank}.", "🐍💥🐱")
        ),
        description = "Magical pink lotus blossoms float on calm waters illuminated by twilight fireflies.",
        rules = listOf(
            "Grand Triple Barge carries up to 3 items.",
            "Carefully balance aquatic and amphibious predator chains."
        )
    )

    // Level 86: Redwood Canyon Rapids
    val LEVEL_86 = PuzzleScenario(
        id = "level_86_redwood_rapids",
        levelNumber = 86,
        title = "Level 86: Redwood Rapids",
        subtitle = "Horse, Apple, Bear, Honey, Wolf, Sheep, and Hay",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.HORSE, GameItem.APPLE, GameItem.BEAR, GameItem.HONEY, GameItem.WOLF, GameItem.SHEEP, GameItem.HAY),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 115L,
        dangerRules = listOf(
            DangerRule(GameItem.BEAR, GameItem.HONEY, "Bear Devoured The Honey!", "You left Bear and Honey alone on the {bank}.", "🐻💥🍯"),
            DangerRule(GameItem.BEAR, GameItem.WOLF, "Bear Clashed With The Wolf!", "You left Bear and Wolf alone on the {bank}.", "🐻💥🐺"),
            DangerRule(GameItem.WOLF, GameItem.HORSE, "Wolf Spooked The Horse!", "You left Wolf and Horse alone on the {bank}.", "🐺💥🐎"),
            DangerRule(GameItem.WOLF, GameItem.SHEEP, "Wolf Attacked The Sheep!", "You left Wolf and Sheep alone on the {bank}.", "🐺💥🐑"),
            DangerRule(GameItem.HORSE, GameItem.APPLE, "Horse Ate The Apples!", "You left Horse and Apple alone on the {bank}.", "🐎💥🍎"),
            DangerRule(GameItem.SHEEP, GameItem.HAY, "Sheep Munched The Hay!", "You left Sheep and Hay alone on the {bank}.", "🐑💥🌾")
        ),
        description = "Towering ancient redwoods shrouded in cool morning mist above rushing currents.",
        rules = listOf(
            "Timber Barge carries up to 3 items.",
            "Prevent apex predator clashes between Bear and Wolf."
        )
    )

    // Level 87: Celestial Starfall Fjord
    val LEVEL_87 = PuzzleScenario(
        id = "level_87_starfall_fjord",
        levelNumber = 87,
        title = "Level 87: Starfall Fjord",
        subtitle = "Eagle, Snake, Monkey, Banana, Tiger, Panda, and Bamboo",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.EAGLE, GameItem.SNAKE, GameItem.MONKEY, GameItem.BANANA, GameItem.TIGER, GameItem.PANDA, GameItem.BAMBOO),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 115L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.PANDA, "Tiger Attacked The Panda!", "You left Tiger and Panda alone on the {bank}.", "🐅💥🐼"),
            DangerRule(GameItem.TIGER, GameItem.MONKEY, "Tiger Pounced On The Monkey!", "You left Tiger and Monkey alone on the {bank}.", "🐅💥🐒"),
            DangerRule(GameItem.EAGLE, GameItem.SNAKE, "Eagle Swooped On The Snake!", "You left Eagle and Snake alone on the {bank}.", "🦅💥🐍"),
            DangerRule(GameItem.SNAKE, GameItem.MONKEY, "Snake Bit The Monkey!", "You left Snake and Monkey alone on the {bank}.", "🐍💥🐒"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Ate The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.PANDA, GameItem.BAMBOO, "Panda Munched The Bamboo!", "You left Panda and Bamboo alone on the {bank}.", "🐼💥🎋")
        ),
        description = "Cosmic shooting stars streak across midnight skies reflected in deep fjord waters.",
        rules = listOf(
            "Starfall Galleon carries up to 3 items.",
            "Coordinate land, tree, and aerial predators across the cosmic divide."
        )
    )

    // Level 88: Sunken Atlantis Aqueduct
    val LEVEL_88 = PuzzleScenario(
        id = "level_88_atlantis_aqueduct",
        levelNumber = 88,
        title = "Level 88: Atlantis Aqueduct",
        subtitle = "Crocodile, Fish, Penguin, Frog, Dragonfly, Cat, Cheese, and Mouse",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.CROCODILE, GameItem.FISH, GameItem.PENGUIN, GameItem.FROG, GameItem.DRAGONFLY, GameItem.CAT, GameItem.CHEESE, GameItem.MOUSE),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 120L,
        dangerRules = listOf(
            DangerRule(GameItem.CROCODILE, GameItem.PENGUIN, "Crocodile Snapped The Penguin!", "You left Crocodile and Penguin alone on the {bank}.", "🐊💥🐧"),
            DangerRule(GameItem.CROCODILE, GameItem.FISH, "Crocodile Swallowed The Fish!", "You left Crocodile and Fish alone on the {bank}.", "🐊💥🐟"),
            DangerRule(GameItem.PENGUIN, GameItem.FISH, "Penguin Devoured The Fish!", "You left Penguin and Fish alone on the {bank}.", "🐧💥🐟"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Caught The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗"),
            DangerRule(GameItem.CAT, GameItem.MOUSE, "Cat Pounced On The Mouse!", "You left Cat and Mouse alone on the {bank}.", "🐱💥🐭"),
            DangerRule(GameItem.CAT, GameItem.FISH, "Cat Clawed The Fish!", "You left Cat and Fish alone on the {bank}.", "🐱💥🐟"),
            DangerRule(GameItem.MOUSE, GameItem.CHEESE, "Mouse Nibbled The Cheese!", "You left Mouse and Cheese alone on the {bank}.", "🐭💥🧀")
        ),
        description = "Sunken marble aqueducts and turquoise tidal pools among ancient ruins.",
        rules = listOf(
            "Imperial Sunken Gondola carries up to 3 items.",
            "Master an 8-item marine and forager labyrinth."
        )
    )

    // Level 89: Dragon's Volcanic Fjord
    val LEVEL_89 = PuzzleScenario(
        id = "level_89_volcanic_dragon",
        levelNumber = 89,
        title = "Level 89: Volcanic Dragon Fjord",
        subtitle = "Tiger, Horse, Apple, Eagle, Snake, Monkey, Banana, and Honey",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.TIGER, GameItem.HORSE, GameItem.APPLE, GameItem.EAGLE, GameItem.SNAKE, GameItem.MONKEY, GameItem.BANANA, GameItem.HONEY),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 120L,
        dangerRules = listOf(
            DangerRule(GameItem.TIGER, GameItem.HORSE, "Tiger Attacked The Horse!", "You left Tiger and Horse alone on the {bank}.", "🐅💥🐎"),
            DangerRule(GameItem.TIGER, GameItem.MONKEY, "Tiger Pounced On The Monkey!", "You left Tiger and Monkey alone on the {bank}.", "🐅💥🐒"),
            DangerRule(GameItem.EAGLE, GameItem.SNAKE, "Eagle Swooped On The Snake!", "You left Eagle and Snake alone on the {bank}.", "🦅💥🐍"),
            DangerRule(GameItem.SNAKE, GameItem.MONKEY, "Snake Bit The Monkey!", "You left Snake and Monkey alone on the {bank}.", "🐍💥🐒"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Snatched The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.MONKEY, GameItem.APPLE, "Monkey Munched The Apples!", "You left Monkey and Apple alone on the {bank}.", "🐒💥🍎"),
            DangerRule(GameItem.HORSE, GameItem.APPLE, "Horse Ate The Apples!", "You left Horse and Apple alone on the {bank}.", "🐎💥🍎")
        ),
        description = "Obsidian basalt cliffs frame glowing orange magma streams under a dramatic fiery sky.",
        rules = listOf(
            "Dragonscale Ferry carries up to 3 items.",
            "Navigate a volatile gauntlet of apex predators and prized orchard delicacies."
        )
    )

    // Level 90: The Grand Celestial River Sovereign
    val LEVEL_90 = PuzzleScenario(
        id = "level_90_celestial_sovereign",
        levelNumber = 90,
        title = "Level 90: The Grand Celestial Sovereign",
        subtitle = "Panda, Bamboo, Monkey, Banana, Eagle, Snake, Frog, and Dragonfly",
        difficulty = PuzzleDifficulty.EXPERT,
        items = listOf(GameItem.PANDA, GameItem.BAMBOO, GameItem.MONKEY, GameItem.BANANA, GameItem.EAGLE, GameItem.SNAKE, GameItem.FROG, GameItem.DRAGONFLY),
        boatCapacity = 3,
        optimalMoves = 7,
        targetTimeSeconds = 130L,
        dangerRules = listOf(
            DangerRule(GameItem.PANDA, GameItem.BAMBOO, "Panda Munched The Bamboo!", "You left Panda and Bamboo alone on the {bank}.", "🐼💥🎋"),
            DangerRule(GameItem.MONKEY, GameItem.BANANA, "Monkey Snatched The Bananas!", "You left Monkey and Banana alone on the {bank}.", "🐒💥🍌"),
            DangerRule(GameItem.EAGLE, GameItem.SNAKE, "Eagle Swooped On The Snake!", "You left Eagle and Snake alone on the {bank}.", "🦅💥🐍"),
            DangerRule(GameItem.EAGLE, GameItem.MONKEY, "Eagle Attacked The Monkey!", "You left Eagle and Monkey alone on the {bank}.", "🦅💥🐒"),
            DangerRule(GameItem.SNAKE, GameItem.FROG, "Snake Struck The Frog!", "You left Snake and Frog alone on the {bank}.", "🐍💥🐸"),
            DangerRule(GameItem.FROG, GameItem.DRAGONFLY, "Frog Caught The Dragonfly!", "You left Frog and Dragonfly alone on the {bank}.", "🐸💥🦗")
        ),
        description = "The supreme 90-level pinnacle! Sovereign golden palace gates spanning the infinite rainbow river of legend.",
        rules = listOf(
            "Supreme Celestial Barge carries up to 3 items.",
            "Master 6 intricate predator and diet rules to achieve ultimate River Crossing Immortality!"
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
        LEVEL_46, LEVEL_47, LEVEL_48, LEVEL_49, LEVEL_50,
        LEVEL_51, LEVEL_52, LEVEL_53, LEVEL_54, LEVEL_55,
        LEVEL_56, LEVEL_57, LEVEL_58, LEVEL_59, LEVEL_60,
        LEVEL_61, LEVEL_62, LEVEL_63, LEVEL_64, LEVEL_65,
        LEVEL_66, LEVEL_67, LEVEL_68, LEVEL_69, LEVEL_70,
        LEVEL_71, LEVEL_72, LEVEL_73, LEVEL_74, LEVEL_75,
        LEVEL_76, LEVEL_77, LEVEL_78, LEVEL_79, LEVEL_80,
        LEVEL_81, LEVEL_82, LEVEL_83, LEVEL_84, LEVEL_85,
        LEVEL_86, LEVEL_87, LEVEL_88, LEVEL_89, LEVEL_90
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
    val catLocation: ItemLocation get() = getItemLocation(GameItem.CAT)
    val fishLocation: ItemLocation get() = getItemLocation(GameItem.FISH)
    val mouseLocation: ItemLocation get() = getItemLocation(GameItem.MOUSE)
    val cheeseLocation: ItemLocation get() = getItemLocation(GameItem.CHEESE)
    val crocodileLocation: ItemLocation get() = getItemLocation(GameItem.CROCODILE)
    val goatLocation: ItemLocation get() = getItemLocation(GameItem.GOAT)
    val carrotLocation: ItemLocation get() = getItemLocation(GameItem.CARROT)
    val chickenLocation: ItemLocation get() = getItemLocation(GameItem.CHICKEN)
    val grainLocation: ItemLocation get() = getItemLocation(GameItem.GRAIN)
    val tigerLocation: ItemLocation get() = getItemLocation(GameItem.TIGER)

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

