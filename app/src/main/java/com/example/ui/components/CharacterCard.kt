package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Bank
import com.example.model.GameItem
import com.example.model.ItemLocation
import com.example.model.RiverState
import com.example.ui.theme.VibrantBackground
import com.example.ui.theme.VibrantPrimary
import com.example.ui.theme.VibrantPrimaryContainer
import com.example.ui.theme.VibrantRiverCanvasFrame
import com.example.ui.theme.VibrantSurfaceBorder
import com.example.ui.theme.VibrantSurfaceVariant
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary

@Composable
fun ItemManagementList(
    riverState: RiverState,
    onToggleItem: (GameItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = riverState.scenario.items
    val capacity = riverState.scenario.boatCapacity
    val currentPassengers = riverState.boatPassengers

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // AAA Quick Passenger Selector Card
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = VibrantSurfaceVariant),
            border = BorderStroke(1.dp, VibrantSurfaceBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SELECT PASSENGER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VibrantPrimary,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                    Text(
                        text = "${currentPassengers.size}/$capacity Seats",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (currentPassengers.size >= capacity) VibrantPrimary else VibrantTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items.forEach { item ->
                        val isSelectedInBoat = item in currentPassengers
                        val location = riverState.getItemLocation(item)
                        val isOnFarmerBank = when (location) {
                            ItemLocation.LEFT_BANK -> riverState.farmerBank == Bank.LEFT
                            ItemLocation.RIGHT_BANK -> riverState.farmerBank == Bank.RIGHT
                            ItemLocation.IN_BOAT -> true
                        }

                        AAAPassengerQuickChip(
                            item = item,
                            isSelected = isSelectedInBoat,
                            isEnabled = isOnFarmerBank && (currentPassengers.size < capacity || isSelectedInBoat),
                            onClick = { onToggleItem(item) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Empty / None Option
                    val isNoneSelected = currentPassengers.isEmpty()
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isNoneSelected) VibrantBackground else Color.White.copy(alpha = 0.6f),
                        border = BorderStroke(
                            if (isNoneSelected) 2.dp else 1.dp,
                            if (isNoneSelected) VibrantPrimary else Color.Transparent
                        ),
                        shadowElevation = if (isNoneSelected) 3.dp else 0.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(enabled = currentPassengers.isNotEmpty()) {
                                currentPassengers.forEach { onToggleItem(it) }
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (isNoneSelected) VibrantPrimaryContainer else VibrantRiverCanvasFrame,
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "None",
                                        tint = if (isNoneSelected) VibrantPrimary else Color(0xFF43474E),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "NONE",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isNoneSelected) VibrantPrimary else Color(0xFF43474E)
                            )
                        }
                    }
                }
            }
        }

        // Detailed AAA Item Status List
        items.forEach { item ->
            AAAItemRowCard(
                item = item,
                riverState = riverState,
                onActionClick = { onToggleItem(item) }
            )
        }
    }
}

@Composable
private fun AAAPassengerQuickChip(
    item: GameItem,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) VibrantBackground else if (isEnabled) Color.White.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.35f),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) VibrantPrimary else Color.Transparent
        ),
        shadowElevation = if (isSelected) 3.dp else 0.dp,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(enabled = isEnabled, onClick = onClick)
            .testTag("quick_select_${item.id}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .then(
                        if (isSelected) Modifier.background(VibrantPrimaryContainer)
                        else Modifier.background(Brush.verticalGradient(listOf(Color(0xFFF1F5F9), Color(0xFFE2E8F0))))
                    )
                    .border(
                        if (isSelected) 2.dp else 1.dp,
                        if (isSelected) VibrantPrimary else Color.White,
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Grounding shadow
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0x55000000), Color.Transparent)
                            )
                        )
                )
                Image(
                    painter = painterResource(id = item.drawableRes),
                    contentDescription = item.displayName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .graphicsLayer {
                            scaleX = item.visualScale
                            scaleY = item.visualScale
                        }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.displayName.uppercase(),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) VibrantPrimary else if (isEnabled) Color(0xFF43474E) else Color.Gray
            )
        }
    }
}

@Composable
fun AAAItemRowCard(
    item: GameItem,
    riverState: RiverState,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val location = riverState.getItemLocation(item)
    val farmerBank = riverState.farmerBank

    val isInBoat = location == ItemLocation.IN_BOAT
    val isOnFarmerBank = when (location) {
        ItemLocation.LEFT_BANK -> farmerBank == Bank.LEFT
        ItemLocation.RIGHT_BANK -> farmerBank == Bank.RIGHT
        ItemLocation.IN_BOAT -> true
    }

    val itemBank = if (location == ItemLocation.LEFT_BANK) Bank.LEFT else Bank.RIGHT
    val isHiddenBank = riverState.isBankHidden(itemBank) && !isInBoat

    val restrictedViolation = if (!isInBoat && isOnFarmerBank) {
        riverState.difficultyModifiers.getBoatCombinationViolation(riverState.boatPassengers + item)
    } else null

    val canBoard = isOnFarmerBank && !isInBoat && (riverState.boatPassengers.size < riverState.scenario.boatCapacity) && (restrictedViolation == null)

    val locationLabel = when {
        isInBoat -> "In Boat 🚣"
        isHiddenBank -> "Misty Shore 🌫️"
        location == ItemLocation.LEFT_BANK -> "Left Bank"
        else -> "Right Bank"
    }

    val locationColor = when {
        isInBoat -> VibrantPrimary
        isHiddenBank -> Color(0xFF64748B)
        location == ItemLocation.LEFT_BANK -> VibrantTextSecondary
        else -> Color(0xFF1565C0)
    }

    val cardBorderColor = if (isInBoat) {
        VibrantPrimary
    } else if (restrictedViolation != null) {
        Color(0xFFF59E0B)
    } else {
        VibrantSurfaceBorder
    }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isInBoat) VibrantPrimaryContainer.copy(alpha = 0.38f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isInBoat) 4.dp else 1.5.dp),
        border = BorderStroke(if (isInBoat) 1.5.dp else 1.dp, cardBorderColor),
        modifier = modifier
            .fillMaxWidth()
            .testTag("item_row_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 3D Avatar and Details
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (isInBoat) Modifier.background(VibrantPrimaryContainer)
                            else Modifier.background(Brush.verticalGradient(listOf(Color(0xFFF8FAFC), Color(0xFFE2E8F0))))
                        )
                        .border(
                            1.5.dp,
                            if (isInBoat) VibrantPrimary else Color(0xFFCBD5E1),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // Soft contact shadow
                    Box(
                        modifier = Modifier
                            .width(38.dp)
                            .height(5.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.radialGradient(
                                    listOf(Color(0x66000000), Color.Transparent)
                                )
                            )
                    )
                    Image(
                        painter = painterResource(id = item.drawableRes),
                        contentDescription = item.displayName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .graphicsLayer {
                                scaleX = item.visualScale
                                scaleY = item.visualScale
                            }
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = VibrantTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = locationColor.copy(alpha = 0.14f)
                        ) {
                            Text(
                                text = locationLabel,
                                color = locationColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = when (item) {
                            GameItem.DOG, GameItem.FOX, GameItem.WOLF, GameItem.LION -> Color(0xFFFFEDD5)
                            GameItem.RABBIT, GameItem.SHEEP -> Color(0xFFDCFCE7)
                            else -> Color(0xFFFEF3C7)
                        }
                    ) {
                        Text(
                            text = item.dangerTag,
                            color = when (item) {
                                GameItem.DOG, GameItem.FOX, GameItem.WOLF, GameItem.LION -> Color(0xFFC2410C)
                                GameItem.RABBIT, GameItem.SHEEP -> Color(0xFF15803D)
                                else -> Color(0xFFB45309)
                            },
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = item.description,
                        fontSize = 11.sp,
                        color = VibrantTextSecondary,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Action Button
            if (isInBoat) {
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VibrantPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_unboard_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Unload",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Unload", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            } else if (canBoard) {
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VibrantPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_board_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBoat,
                        contentDescription = "Board boat",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Board", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                OutlinedButton(
                    onClick = {},
                    enabled = false,
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (restrictedViolation != null) "Restricted ⛔" else if (!isOnFarmerBank) "Far Bank" else "Boat Full",
                        fontSize = 10.sp,
                        color = if (restrictedViolation != null) Color(0xFFB45309) else Color.Gray
                    )
                }
            }
        }
    }
}
