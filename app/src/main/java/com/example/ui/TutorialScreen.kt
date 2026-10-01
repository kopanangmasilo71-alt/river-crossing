package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameItem
import com.example.ui.components.WoodCard
import com.example.ui.components.WoodFilterPill
import com.example.ui.components.WoodInsetBox
import com.example.ui.components.WoodScreenContainer
import com.example.ui.components.WoodTopAppBar
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodSignboardLight
import com.example.ui.theme.WoodTextMuted

private enum class TutorialTab(val title: String, val icon: ImageVector) {
    WALKTHROUGH("Solution Guide", Icons.Default.School),
    RULES("Core Rules", Icons.Default.Security),
    STRATEGIES("Pro Tactics", Icons.Default.Lightbulb),
    BESTIARY("Characters", Icons.Default.AutoAwesome)
}

private data class WalkthroughStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String,
    val explanation: String,
    val leftBank: List<GameItem>,
    val rightBank: List<GameItem>,
    val inBoat: List<GameItem>,
    val farmerBankName: String,
    val actionButtonText: String
)

@Composable
fun TutorialScreen(
    onBack: () -> Unit,
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(TutorialTab.WALKTHROUGH) }

    WoodScreenContainer(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            WoodTopAppBar(
                title = "River Academy",
                subtitle = "Master Crossing Tactics",
                onBack = onBack,
                actions = {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.dp, GoldenBankGlow),
                        modifier = Modifier.clickable(onClick = onStartGame)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Level 1",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Play",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow
                            )
                        }
                    }
                }
            )

            // Wood Filter Pill Tab Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TutorialTab.entries) { tab ->
                    WoodFilterPill(
                        text = tab.title,
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }

            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TutorialTabContent",
                modifier = Modifier.weight(1f)
            ) { tab ->
                when (tab) {
                    TutorialTab.WALKTHROUGH -> InteractiveWalkthroughTab(onStartGame = onStartGame)
                    TutorialTab.RULES -> CoreRulesTab(onStartGame = onStartGame)
                    TutorialTab.STRATEGIES -> ProStrategiesTab(onStartGame = onStartGame)
                    TutorialTab.BESTIARY -> CharacterBestiaryTab()
                }
            }
        }
    }
}

@Composable
private fun InteractiveWalkthroughTab(onStartGame: () -> Unit) {
    val steps = remember {
        listOf(
            WalkthroughStep(
                stepNumber = 1,
                title = "1. The Dilemma",
                instruction = "Take the Rabbit across first!",
                explanation = "Wolf won't eat Cabbage if left alone. But if you take Wolf first, Rabbit eats Cabbage. If you take Cabbage first, Wolf eats Rabbit!",
                leftBank = listOf(GameItem.WOLF, GameItem.CABBAGE),
                rightBank = emptyList(),
                inBoat = listOf(GameItem.RABBIT),
                farmerBankName = "Crossing to Right Bank",
                actionButtonText = "Row Rabbit to Right Bank ➡️"
            ),
            WalkthroughStep(
                stepNumber = 2,
                title = "2. Drop Off & Return",
                instruction = "Unload Rabbit on Right Bank and return alone.",
                explanation = "Rabbit is now safe on the Right Bank. Left Bank has Wolf & Cabbage (who do not harm each other). Farmer rows back alone.",
                leftBank = listOf(GameItem.WOLF, GameItem.CABBAGE),
                rightBank = listOf(GameItem.RABBIT),
                inBoat = emptyList(),
                farmerBankName = "Returning to Left Bank",
                actionButtonText = "Row Back Alone ⬅️"
            ),
            WalkthroughStep(
                stepNumber = 3,
                title = "3. Transport the Wolf",
                instruction = "Load the Wolf and row across to the Right Bank.",
                explanation = "You now ferry the Wolf. Only Cabbage is left on the Left Bank, completely safe.",
                leftBank = listOf(GameItem.CABBAGE),
                rightBank = listOf(GameItem.RABBIT),
                inBoat = listOf(GameItem.WOLF),
                farmerBankName = "Arriving at Right Bank",
                actionButtonText = "Land with Wolf ➡️"
            ),
            WalkthroughStep(
                stepNumber = 4,
                title = "4. The Master 'Take-Back' Move ⭐",
                instruction = "Unload the Wolf, but pick up the Rabbit to take back!",
                explanation = "CRITICAL TRICK: You cannot leave Wolf and Rabbit together on the Right Bank! So take the Rabbit BACK in the boat with you.",
                leftBank = listOf(GameItem.CABBAGE),
                rightBank = listOf(GameItem.WOLF),
                inBoat = listOf(GameItem.RABBIT),
                farmerBankName = "Bringing Rabbit Back ⬅️",
                actionButtonText = "Ferry Rabbit Back to Left Bank ⬅️"
            ),
            WalkthroughStep(
                stepNumber = 5,
                title = "5. Swap for the Cabbage",
                instruction = "Unload Rabbit on Left Bank, Load Cabbage & row across!",
                explanation = "On the Left Bank, you leave Rabbit safely alone and take Cabbage across to join the Wolf.",
                leftBank = listOf(GameItem.RABBIT),
                rightBank = listOf(GameItem.WOLF),
                inBoat = listOf(GameItem.CABBAGE),
                farmerBankName = "Ferrying Cabbage ➡️",
                actionButtonText = "Deliver Cabbage ➡️"
            ),
            WalkthroughStep(
                stepNumber = 6,
                title = "6. Safe Delivery & Return",
                instruction = "Leave Cabbage with Wolf and row back alone.",
                explanation = "Wolf and Cabbage do not eat each other! The Right Bank is safe. Farmer rows back alone to fetch the final item.",
                leftBank = listOf(GameItem.RABBIT),
                rightBank = listOf(GameItem.WOLF, GameItem.CABBAGE),
                inBoat = emptyList(),
                farmerBankName = "Final Return Trip ⬅️",
                actionButtonText = "Row back for Rabbit ⬅️"
            ),
            WalkthroughStep(
                stepNumber = 7,
                title = "7. Final Victory!",
                instruction = "Load the Rabbit and row across for the win!",
                explanation = "Bring the Rabbit to the Right Bank. All 3 items and the Farmer are safely across in exactly 7 optimal moves! 🌟🌟🌟",
                leftBank = emptyList(),
                rightBank = listOf(GameItem.WOLF, GameItem.CABBAGE, GameItem.RABBIT),
                inBoat = emptyList(),
                farmerBankName = "All Safely on Right Bank! 🎉",
                actionButtonText = "Complete Tutorial & Play Level 1 🏆"
            )
        )
    }

    var currentStepIdx by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIdx]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            WoodCard(shape = RoundedCornerShape(20.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = WoodInsetPanel,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🎓", fontSize = 22.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Interactive 7-Step Solution",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = WoodGoldenText
                        )
                        Text(
                            text = "Step ${currentStepIdx + 1} of ${steps.size}: Master the classic river riddle",
                            fontSize = 12.sp,
                            color = WoodTextMuted
                        )
                    }
                }
            }
        }

        item {
            // Step Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in steps.indices) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                if (i <= currentStepIdx) GoldenBankGlow else WoodInsetPanel
                            )
                            .clickable { currentStepIdx = i }
                    )
                }
            }
        }

        item {
            WoodCard(shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentStep.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldenBankGlow
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WoodInsetPanel,
                            border = BorderStroke(1.dp, GoldenBankGlow)
                        ) {
                            Text(
                                text = currentStep.farmerBankName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // River Visual Simulation
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF2E4E28), // Left Green Bank
                                        Color(0xFF1E3A5F), // River Center
                                        Color(0xFF2E4E28)  // Right Green Bank
                                    )
                                )
                            )
                            .border(1.5.dp, WoodSignboardBorder, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left Bank Items
                            Column(
                                modifier = Modifier
                                    .width(90.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(WoodInsetPanel.copy(alpha = 0.9f))
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("LEFT BANK", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldenBankGlow)
                                if (currentStep.leftBank.isEmpty()) {
                                    Text("Empty", fontSize = 10.sp, color = WoodTextMuted)
                                } else {
                                    currentStep.leftBank.forEach { item ->
                                        MiniItemBadge(item = item)
                                    }
                                }
                            }

                            // River Center with Boat
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = WoodButtonBottom,
                                    border = BorderStroke(1.5.dp, GoldenBankGlow),
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(6.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DirectionsBoat,
                                                contentDescription = "Boat",
                                                tint = GoldenBankGlow,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Boat",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = GoldenBankGlow
                                            )
                                        }

                                        if (currentStep.inBoat.isNotEmpty()) {
                                            Spacer(modifier = Modifier.height(2.dp))
                                            currentStep.inBoat.forEach { item ->
                                                MiniItemBadge(item = item, inBoat = true)
                                            }
                                        }
                                    }
                                }
                            }

                            // Right Bank Items
                            Column(
                                modifier = Modifier
                                    .width(90.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(WoodInsetPanel.copy(alpha = 0.9f))
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("RIGHT BANK", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldenBankGlow)
                                if (currentStep.rightBank.isEmpty()) {
                                    Text("Empty", fontSize = 10.sp, color = WoodTextMuted)
                                } else {
                                    currentStep.rightBank.forEach { item ->
                                        MiniItemBadge(item = item)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Instruction Banner
                    WoodInsetBox(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "👉 ${currentStep.instruction}",
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentStep.explanation,
                                fontSize = 12.sp,
                                color = WoodGoldenText,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Navigation Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WoodInsetPanel,
                            border = BorderStroke(1.dp, WoodSignboardBorder),
                            modifier = Modifier
                                .clickable(enabled = currentStepIdx > 0) {
                                    if (currentStepIdx > 0) currentStepIdx--
                                }
                                .testTag("tutorial_prev_step")
                        ) {
                            Text(
                                text = "Previous",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentStepIdx > 0) WoodGoldenText else WoodTextMuted,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WoodButtonBottom,
                            border = BorderStroke(1.dp, GoldenBankGlow),
                            modifier = Modifier
                                .clickable {
                                    if (currentStepIdx < steps.size - 1) {
                                        currentStepIdx++
                                    } else {
                                        onStartGame()
                                    }
                                }
                                .testTag("tutorial_next_step")
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(WoodButtonTop, WoodButtonBottom)
                                        )
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = if (currentStepIdx == steps.size - 1) "Play Game Now 🏆" else "Next Step ➡️",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = GoldenBankGlow
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            // Quick Reset or Jump to Level 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, WoodSignboardBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { currentStepIdx = 0 }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = WoodGoldenText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Restart",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = WoodGoldenText
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = WoodButtonBottom,
                    border = BorderStroke(1.dp, GoldenBankGlow),
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onStartGame)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(WoodButtonTop, WoodButtonBottom)
                                )
                            )
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Start Level 1",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldenBankGlow
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniItemBadge(item: GameItem, inBoat: Boolean = false) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (inBoat) WoodSignboardLight else WoodInsetPanel,
        border = BorderStroke(1.dp, if (inBoat) GoldenBankGlow else WoodSignboardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.drawableRes),
                contentDescription = item.displayName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = item.displayName,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (inBoat) GoldenBankGlow else WoodGoldenText
            )
        }
    }
}

@Composable
private fun CoreRulesTab(onStartGame: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            RuleCard(
                emoji = "👨‍🌾",
                title = "Rule 1: The Helmsman Principle",
                description = "The boat cannot cross the river alone. The Farmer (or Captain) MUST row the boat on every single trip from bank to bank."
            )
        }

        item {
            RuleCard(
                emoji = "⛵",
                title = "Rule 2: Boat Capacity Limit",
                description = "Standard riverboats hold the Farmer plus 1 passenger item (e.g. Wolf, Rabbit, or Cabbage). Advanced levels feature 2-seat and 3-seat vessels!"
            )
        }

        item {
            RuleCard(
                emoji = "⚠️",
                title = "Rule 3: Predator & Prey Absence Rule",
                description = "When the Farmer is on the same bank, all animals and items remain calm and behave. BUT the moment the Farmer leaves, predator pairs interact instantly:\n• Wolf eats Rabbit / Sheep\n• Rabbit / Sheep eats Cabbage\n• Fox eats Goose / Chicken\n• Goose eats Corn / Grain\n• Hawk hunts Mouse / Snake\n• Dog fights Wolf"
            )
        }

        item {
            RuleCard(
                emoji = "🔄",
                title = "Rule 4: Multi-Directional Ferrying",
                description = "Items can be transported in BOTH directions! You do not only take items to the right; taking an item BACK to the starting bank is often the key to solving the puzzle."
            )
        }

        item {
            RuleCard(
                emoji = "🏆",
                title = "Rule 5: Victory Condition",
                description = "A level is won when all items and the Farmer are safely on the Right Bank without any rule violations at any point in the journey."
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = WoodButtonBottom,
                border = BorderStroke(1.5.dp, GoldenBankGlow),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(onClick = onStartGame)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(WoodButtonTop, WoodButtonBottom)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Understood! Let's Play Level 1 🎮",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = GoldenBankGlow
                    )
                }
            }
        }
    }
}

@Composable
private fun ProStrategiesTab(onStartGame: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            StrategyCard(
                badge = "STRATEGY 1",
                title = "The Cargo Take-Back Technique",
                explanation = "When you encounter a deadlock where any unloaded item would cause a fight with an item already on that bank, board the conflicting passenger into the boat with you on the return trip!"
            )
        }

        item {
            StrategyCard(
                badge = "STRATEGY 2",
                title = "Identify the 'Buffer' Item",
                explanation = "In classic 3-item puzzles, the middle item (e.g. Rabbit or Sheep) is the buffer: it conflicts with both the predator and the food. It MUST always cross first and be swapped during return trips."
            )
        }

        item {
            StrategyCard(
                badge = "STRATEGY 3",
                title = "Least Moves Parity",
                explanation = "Every round trip takes 2 moves (Forward + Return). To earn 3 Gold Stars, solve each scenario in the mathematical optimal move count (e.g., 7 moves for Classic 3-item, 11 moves for 4-item)."
            )
        }

        item {
            StrategyCard(
                badge = "STRATEGY 4",
                title = "Use Unlimited Undo & AI BFS Hints",
                explanation = "Made a wrong turn? Tap the Undo button freely without score penalties. Tap 'AI Hint' to receive the shortest-path graph search recommendation calculated in real-time."
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = WoodButtonBottom,
                border = BorderStroke(1.5.dp, GoldenBankGlow),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(onClick = onStartGame)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(WoodButtonTop, WoodButtonBottom)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Put Strategies to the Test 🚀",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = GoldenBankGlow
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterBestiaryTab() {
    val characters = remember {
        listOf(
            Triple(GameItem.LION, "Apex Predator", "King of beasts! Attacks Wolves, Dogs & Sheep if left unattended."),
            Triple(GameItem.WOLF, "Fierce Carnivore", "Attacks Sheep, Dogs & Rabbits if left alone without Farmer."),
            Triple(GameItem.FOX, "Cunning Hunter", "Preys on Rabbits & fights with Dogs if left unattended."),
            Triple(GameItem.DOG, "Loyal Guardian", "Guards against danger, but chases Rabbits/Sheep and fights Wolves/Foxes."),
            Triple(GameItem.SHEEP, "Docile Grazer", "Gentle grazer that eats Hay, Corn & Cabbage, but is prey to Wolves & Lions."),
            Triple(GameItem.RABBIT, "Quick Herbivore", "Eats Cabbage, Corn & Hay. The classic riddle buffer item!"),
            Triple(GameItem.CABBAGE, "Fresh Crop", "Delicious cabbage heads eaten by Rabbits & Sheep if unattended."),
            Triple(GameItem.CORN, "Golden Grain", "Fresh ear of sweet corn, vulnerable to hungry livestock."),
            Triple(GameItem.HAY, "Dry Forage", "Sweet golden hay bales that feed Sheep & Rabbits.")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(characters) { (item, role, desc) ->
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.drawableRes),
                        contentDescription = item.displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, GoldenBankGlow, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.displayName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = WoodGoldenText
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WoodInsetPanel
                            ) {
                                Text(
                                    text = role,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = GoldenBankGlow,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = desc,
                            fontSize = 11.5.sp,
                            color = WoodTextMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RuleCard(emoji: String, title: String, description: String) {
    WoodCard(shape = RoundedCornerShape(18.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = CircleShape,
                color = WoodInsetPanel,
                border = BorderStroke(1.5.dp, GoldenBankGlow),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = emoji, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = WoodGoldenText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = WoodTextMuted,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun StrategyCard(badge: String, title: String, explanation: String) {
    WoodCard(shape = RoundedCornerShape(18.dp)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = WoodInsetPanel,
                border = BorderStroke(1.dp, GoldenBankGlow)
            ) {
                Text(
                    text = badge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldenBankGlow,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WoodGoldenText
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = explanation,
                fontSize = 12.sp,
                color = WoodTextMuted,
                lineHeight = 17.sp
            )
        }
    }
}
