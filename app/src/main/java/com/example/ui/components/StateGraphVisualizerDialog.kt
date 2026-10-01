package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithm.DiscreteState
import com.example.algorithm.RiverCrossingSolver
import com.example.model.GameItem
import com.example.model.RiverState
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodTextMuted

@Composable
fun StateGraphVisualizerDialog(
    currentState: RiverState,
    isAutoSolving: Boolean,
    onStartAutoSolve: () -> Unit,
    onStopAutoSolve: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val analysis = remember { RiverCrossingSolver.analyzeStateSpace() }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isAutoSolving) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFDC2626),
                        border = BorderStroke(1.5.dp, Color(0xFFFF7A7A)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onStopAutoSolve)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop", tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Stop Solver", fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 13.sp)
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = WoodButtonBottom,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onDismiss()
                                onStartAutoSolve()
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(WoodButtonTop, WoodButtonBottom)
                                    )
                                )
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Auto Solve", tint = GoldenBankGlow)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Demonstrate BFS", fontWeight = FontWeight.ExtraBold, color = GoldenBankGlow, fontSize = 13.sp)
                            }
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable(onClick = onDismiss)
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Close", color = Color(0xFFBAE6FD), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountTree,
                        contentDescription = null,
                        tint = GoldenBankGlow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AI State Space Explorer",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color(0xFFBAE6FD))
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = WoodInsetPanel,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = GoldenBankGlow
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                "Optimal Path (7 Steps)",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == 0) Color.White else Color(0xFFBAE6FD)
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "All States (16)",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == 1) Color.White else Color(0xFFBAE6FD)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (selectedTab == 0) {
                    // Optimal Path List
                    OptimalPathView(
                        optimalPath = analysis.optimalPath,
                        currentSimpleState = currentState.toSimpleStateString()
                    )
                } else {
                    // All States Breakdown
                    AllStatesView(analysis = analysis)
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xF2072449),
        modifier = modifier
    )
}

@Composable
private fun OptimalPathView(
    optimalPath: List<Pair<DiscreteState, GameItem?>>,
    currentSimpleState: String
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            // Initial State Node (Step 0)
            StateStepCard(
                stepIndex = 0,
                stateLabel = "(L, L, L, L)",
                actionLabel = "Start: All items on Left Bank",
                isCurrent = currentSimpleState == "(L, L, L, L)",
                isGoal = false
            )
        }

        itemsIndexed(optimalPath) { index, (state, movedItem) ->
            val isGoal = state.isGoal()
            val actionText = if (movedItem != null) {
                "Take ${movedItem.displayName} ${movedItem.emoji} to ${state.farmer.displayName}"
            } else {
                "Farmer returns alone to ${state.farmer.displayName}"
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = GoldenBankGlow.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                StateStepCard(
                    stepIndex = index + 1,
                    stateLabel = state.toLabel(),
                    actionLabel = actionText,
                    isCurrent = currentSimpleState.startsWith(state.toLabel().substring(0, 4)),
                    isGoal = isGoal
                )
            }
        }
    }
}

@Composable
private fun StateStepCard(
    stepIndex: Int,
    stateLabel: String,
    actionLabel: String,
    isCurrent: Boolean,
    isGoal: Boolean
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isCurrent) WoodButtonBottom else WoodInsetPanel,
        border = BorderStroke(
            1.dp,
            if (isCurrent) GoldenBankGlow else if (isGoal) GoldenBankGlow else WoodSignboardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = if (isCurrent) GoldenBankGlow else WoodSignboardDark,
                    border = BorderStroke(1.dp, GoldenBankGlow),
                    modifier = Modifier.size(22.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$stepIndex",
                            color = if (isCurrent) WoodSignboardDark else GoldenBankGlow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = actionLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCurrent) GoldenBankGlow else WoodGoldenText
                    )
                    Text(
                        text = "State (F, D, R, C): $stateLabel",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = WoodTextMuted
                    )
                }
            }

            if (isCurrent) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = GoldenBankGlow
                ) {
                    Text(
                        text = "ACTIVE",
                        color = WoodSignboardDark,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            } else if (isGoal) {
                Text(text = "🏁", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun AllStatesView(analysis: RiverCrossingSolver.StateAnalysis) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = WoodInsetPanel,
                border = BorderStroke(1.dp, WoodSignboardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total Combinations: 2⁴ = 16 states\nValid States: 10 (Safe)\nIllegal States: 6 (Violations)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = WoodGoldenText,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        itemsIndexed(analysis.allStates) { _, state ->
            val isValid = state.isValid()
            val isOptimal = analysis.optimalPath.any { it.first == state } || state == RiverCrossingSolver.INITIAL_STATE

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isValid) WoodInsetPanel else Color(0xFF3B1A1A),
                border = BorderStroke(
                    1.dp,
                    if (isValid) WoodSignboardBorder else Color(0xFFDC2626).copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = state.toLabel(),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (isValid) GoldenBankGlow else Color(0xFFFF7A7A)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isValid) "✓ Valid" else "✗ Violation",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isValid) GoldenBankGlow else Color(0xFFFF7A7A)
                        )
                    }

                    if (isOptimal) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WoodButtonBottom,
                            border = BorderStroke(1.dp, GoldenBankGlow)
                        ) {
                            Text(
                                text = "Optimal",
                                color = GoldenBankGlow,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
