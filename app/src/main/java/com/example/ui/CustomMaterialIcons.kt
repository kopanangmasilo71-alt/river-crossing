package androidx.compose.material.icons.filled

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private fun icon(name: String, block: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = "Filled.$name",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply(block).build()

private var _accountTree: ImageVector? = null
public val Icons.Filled.AccountTree: ImageVector
    get() {
        if (_accountTree != null) return _accountTree!!
        _accountTree = icon("AccountTree") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(22.0f, 11.0f)
                verticalLineTo(3.0f)
                horizontalLineToRelative(-7.0f)
                verticalLineToRelative(3.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(2f)
                verticalLineToRelative(8.0f)
                horizontalLineToRelative(7.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(10.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(-8.0f)
                horizontalLineToRelative(-7.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(3.0f)
                close()
            }
        }
        return _accountTree!!
    }

private var _apps: ImageVector? = null
public val Icons.Filled.Apps: ImageVector
    get() {
        if (_apps != null) return _apps!!
        _apps = icon("Apps") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4.0f, 8.0f)
                horizontalLineToRelative(4.0f)
                lineTo(8.0f, 4.0f)
                lineTo(4.0f, 4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(10.0f, 20.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(4.0f, 20.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                lineTo(4.0f, 16.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(4.0f, 14.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                lineTo(4.0f, 10.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(10.0f, 14.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(16.0f, 4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(4.0f)
                lineTo(20.0f, 4.0f)
                horizontalLineToRelative(-4.0f)
                close()
                moveTo(10.0f, 8.0f)
                horizontalLineToRelative(4.0f)
                lineTo(14.0f, 4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(16.0f, 14.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(16.0f, 20.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                close()
            }
        }
        return _apps!!
    }

private var _arrowDownward: ImageVector? = null
public val Icons.Filled.ArrowDownward: ImageVector
    get() {
        if (_arrowDownward != null) return _arrowDownward!!
        _arrowDownward = icon("ArrowDownward") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 12.0f)
                lineToRelative(-1.41f, -1.41f)
                lineTo(13.0f, 16.17f)
                verticalLineTo(4.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(12.17f)
                lineToRelative(-5.58f, -5.59f)
                lineTo(4.0f, 12.0f)
                lineToRelative(8.0f, 8.0f)
                lineToRelative(8.0f, -8.0f)
                close()
            }
        }
        return _arrowDownward!!
    }

private var _autoAwesome: ImageVector? = null
public val Icons.Filled.AutoAwesome: ImageVector
    get() {
        if (_autoAwesome != null) return _autoAwesome!!
        _autoAwesome = icon("AutoAwesome") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.0f, 9.0f)
                lineToRelative(1.25f, -2.75f)
                lineTo(23.0f, 5.0f)
                lineToRelative(-2.75f, -1.25f)
                lineTo(19.0f, 1f)
                lineToRelative(-1.25f, 2.75f)
                lineTo(15.0f, 5.0f)
                lineToRelative(2.75f, 1.25f)
                lineTo(19.0f, 9.0f)
                close()
                moveTo(11.5f, 9.5f)
                lineTo(9.0f, 4.0f)
                lineTo(6.5f, 9.5f)
                lineTo(1f, 12.0f)
                lineToRelative(5.5f, 2.5f)
                lineTo(9.0f, 20.0f)
                lineToRelative(2.5f, -5.5f)
                lineTo(17.0f, 12.0f)
                lineToRelative(-5.5f, -2.5f)
                close()
                moveTo(19.0f, 15.0f)
                lineToRelative(-1.25f, 2.75f)
                lineTo(15.0f, 19.0f)
                lineToRelative(2.75f, 1.25f)
                lineTo(19.0f, 23.0f)
                lineToRelative(1.25f, -2.75f)
                lineTo(23.0f, 19.0f)
                lineToRelative(-2.75f, -1.25f)
                lineTo(19.0f, 15.0f)
                close()
            }
        }
        return _autoAwesome!!
    }

private var _chevronRight: ImageVector? = null
public val Icons.Filled.ChevronRight: ImageVector
    get() {
        if (_chevronRight != null) return _chevronRight!!
        _chevronRight = icon("ChevronRight") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.0f, 6.0f)
                lineTo(8.59f, 7.41f)
                lineTo(13.17f, 12.0f)
                lineToRelative(-4.58f, 4.59f)
                lineTo(10.0f, 18.0f)
                lineToRelative(6.0f, -6.0f)
                close()
            }
        }
        return _chevronRight!!
    }

private var _deleteOutline: ImageVector? = null
public val Icons.Filled.DeleteOutline: ImageVector
    get() {
        if (_deleteOutline != null) return _deleteOutline!!
        _deleteOutline = icon("DeleteOutline") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 19.0f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2.0f)
                lineTo(18.0f, 7.0f)
                lineTo(6.0f, 7.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(8.0f, 9.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(10.0f)
                lineTo(8.0f, 19.0f)
                lineTo(8.0f, 9.0f)
                close()
                moveTo(15.5f, 4.0f)
                lineToRelative(-1.0f, -1.0f)
                horizontalLineToRelative(-5.0f)
                lineToRelative(-1.0f, 1f)
                lineTo(5.0f, 4.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(14.0f)
                lineTo(19.0f, 4.0f)
                close()
            }
        }
        return _deleteOutline!!
    }

private var _directionsBoat: ImageVector? = null
public val Icons.Filled.DirectionsBoat: ImageVector
    get() {
        if (_directionsBoat != null) return _directionsBoat!!
        _directionsBoat = icon("DirectionsBoat") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 21.0f)
                curveToRelative(-1.39f, 0f, -2.78f, -0.47f, -4.0f, -1.32f)
                curveToRelative(-2.44f, 1.71f, -5.56f, 1.71f, -8.0f, 0f)
                curveTo(6.78f, 20.53f, 5.39f, 21.0f, 4.0f, 21.0f)
                horizontalLineTo(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                curveToRelative(1.38f, 0f, 2.74f, -0.35f, 4.0f, -0.99f)
                curveToRelative(2.52f, 1.29f, 5.48f, 1.29f, 8.0f, 0f)
                curveToRelative(1.26f, 0.65f, 2.62f, 0.99f, 4.0f, 0.99f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                close()
                moveTo(3.95f, 19.0f)
                horizontalLineTo(4.0f)
                curveToRelative(1.6f, 0f, 3.02f, -0.88f, 4.0f, -2.0f)
                curveToRelative(0.98f, 1.12f, 2.4f, 2f, 4.0f, 2f)
                reflectiveCurveToRelative(3.02f, -0.88f, 4.0f, -2.0f)
                curveToRelative(0.98f, 1.12f, 2.4f, 2f, 4.0f, 2f)
                horizontalLineToRelative(0.05f)
                lineToRelative(1.89f, -6.68f)
                curveToRelative(0.08f, -0.26f, 0.06f, -0.54f, -0.06f, -0.78f)
                reflectiveCurveToRelative(-0.34f, -0.42f, -0.6f, -0.5f)
                lineTo(20.0f, 10.62f)
                verticalLineTo(6.0f)
                curveToRelative(0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-3.0f)
                verticalLineTo(1f)
                horizontalLineTo(9.0f)
                verticalLineToRelative(3.0f)
                horizontalLineTo(6.0f)
                curveToRelative(-1.1f, 0f, -2.0f, 0.9f, -2.0f, 2f)
                verticalLineToRelative(4.62f)
                lineToRelative(-1.29f, 0.42f)
                curveToRelative(-0.26f, 0.08f, -0.48f, 0.26f, -0.6f, 0.5f)
                reflectiveCurveToRelative(-0.15f, 0.52f, -0.06f, 0.78f)
                lineTo(3.95f, 19.0f)
                close()
                moveTo(6.0f, 6.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(3.97f)
                lineTo(12.0f, 8.0f)
                lineTo(6.0f, 9.97f)
                verticalLineTo(6.0f)
                close()
            }
        }
        return _directionsBoat!!
    }

private var _emojiEvents: ImageVector? = null
public val Icons.Filled.EmojiEvents: ImageVector
    get() {
        if (_emojiEvents != null) return _emojiEvents!!
        _emojiEvents = icon("EmojiEvents") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.0f, 5.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(7.0f)
                verticalLineToRelative(2f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 5.0f, 3.0f, 5.9f, 3.0f, 7.0f)
                verticalLineToRelative(1f)
                curveToRelative(0f, 2.55f, 1.92f, 4.63f, 4.39f, 4.94f)
                curveToRelative(0.63f, 1.5f, 1.98f, 2.63f, 3.61f, 2.96f)
                verticalLineTo(19.0f)
                horizontalLineTo(7.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(-3.1f)
                curveToRelative(1.63f, -0.33f, 2.98f, -1.46f, 3.61f, -2.96f)
                curveTo(19.08f, 12.63f, 21.0f, 10.55f, 21.0f, 8.0f)
                verticalLineTo(7.0f)
                curveTo(21.0f, 5.9f, 20.1f, 5.0f, 19.0f, 5.0f)
                close()
                moveTo(5.0f, 8.0f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(3.82f)
                curveTo(5.84f, 10.4f, 5.0f, 9.3f, 5.0f, 8.0f)
                close()
                moveTo(19.0f, 8.0f)
                curveToRelative(0f, 1.3f, -0.84f, 2.4f, -2.0f, 2.82f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(2f)
                verticalLineTo(8.0f)
                close()
            }
        }
        return _emojiEvents!!
    }

private var _expandLess: ImageVector? = null
public val Icons.Filled.ExpandLess: ImageVector
    get() {
        if (_expandLess != null) return _expandLess!!
        _expandLess = icon("ExpandLess") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 8.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(1.41f, 1.41f)
                lineTo(12.0f, 10.83f)
                lineToRelative(4.59f, 4.58f)
                lineTo(18.0f, 14.0f)
                close()
            }
        }
        return _expandLess!!
    }

private var _expandMore: ImageVector? = null
public val Icons.Filled.ExpandMore: ImageVector
    get() {
        if (_expandMore != null) return _expandMore!!
        _expandMore = icon("ExpandMore") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.59f, 8.59f)
                lineTo(12.0f, 13.17f)
                lineTo(7.41f, 8.59f)
                lineTo(6.0f, 10.0f)
                lineToRelative(6.0f, 6.0f)
                lineToRelative(6.0f, -6.0f)
                close()
            }
        }
        return _expandMore!!
    }

private var _flag: ImageVector? = null
public val Icons.Filled.Flag: ImageVector
    get() {
        if (_flag != null) return _flag!!
        _flag = icon("Flag") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(14.4f, 6.0f)
                lineTo(14.0f, 4.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(17.0f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-7.0f)
                horizontalLineToRelative(5.6f)
                lineToRelative(0.4f, 2f)
                horizontalLineToRelative(7.0f)
                verticalLineTo(6.0f)
                close()
            }
        }
        return _flag!!
    }

private var _helpOutline: ImageVector? = null
public val Icons.Filled.HelpOutline: ImageVector
    get() {
        if (_helpOutline != null) return _helpOutline!!
        _helpOutline = icon("HelpOutline") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.0f, 18.0f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(2f)
                close()
                moveTo(12.0f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12.0f)
                reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2f, 12.0f, 2f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-4.41f, 0f, -8.0f, -3.59f, -8.0f, -8.0f)
                reflectiveCurveToRelative(3.59f, -8.0f, 8.0f, -8.0f)
                reflectiveCurveToRelative(8.0f, 3.59f, 8.0f, 8.0f)
                reflectiveCurveToRelative(-3.59f, 8.0f, -8.0f, 8.0f)
                close()
                moveTo(12.0f, 6.0f)
                curveToRelative(-2.21f, 0f, -4.0f, 1.79f, -4.0f, 4.0f)
                horizontalLineToRelative(2f)
                curveToRelative(0f, -1.1f, 0.9f, -2.0f, 2f, -2.0f)
                reflectiveCurveToRelative(2f, 0.9f, 2f, 2f)
                curveToRelative(0f, 2f, -3.0f, 1.75f, -3.0f, 5.0f)
                horizontalLineToRelative(2f)
                curveToRelative(0f, -2.25f, 3.0f, -2.5f, 3.0f, -5.0f)
                curveToRelative(0f, -2.21f, -1.79f, -4.0f, -4.0f, -4.0f)
                close()
            }
        }
        return _helpOutline!!
    }

private var _history: ImageVector? = null
public val Icons.Filled.History: ImageVector
    get() {
        if (_history != null) return _history!!
        _history = icon("History") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13.0f, 3.0f)
                curveToRelative(-4.97f, 0f, -9.0f, 4.03f, -9.0f, 9.0f)
                lineTo(1f, 12.0f)
                lineToRelative(3.89f, 3.89f)
                lineToRelative(0.07f, 0.14f)
                lineTo(9.0f, 12.0f)
                lineTo(6.0f, 12.0f)
                curveToRelative(0f, -3.87f, 3.13f, -7.0f, 7.0f, -7.0f)
                reflectiveCurveToRelative(7.0f, 3.13f, 7.0f, 7.0f)
                reflectiveCurveToRelative(-3.13f, 7.0f, -7.0f, 7.0f)
                curveToRelative(-1.93f, 0f, -3.68f, -0.79f, -4.94f, -2.06f)
                lineToRelative(-1.42f, 1.42f)
                curveTo(8.27f, 19.99f, 10.51f, 21.0f, 13.0f, 21.0f)
                curveToRelative(4.97f, 0f, 9.0f, -4.03f, 9.0f, -9.0f)
                reflectiveCurveToRelative(-4.03f, -9.0f, -9.0f, -9.0f)
                close()
                moveTo(12.0f, 8.0f)
                verticalLineToRelative(5.0f)
                lineToRelative(4.28f, 2.54f)
                lineToRelative(0.72f, -1.21f)
                lineToRelative(-3.5f, -2.08f)
                lineTo(13.5f, 8.0f)
                lineTo(12.0f, 8.0f)
                close()
            }
        }
        return _history!!
    }

private var _lightbulb: ImageVector? = null
public val Icons.Filled.Lightbulb: ImageVector
    get() {
        if (_lightbulb != null) return _lightbulb!!
        _lightbulb = icon("Lightbulb") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.0f, 21.0f)
                curveToRelative(0f, 0.5f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.6f, 0f, 1f, -0.5f, 1f, -1.0f)
                verticalLineToRelative(-1.0f)
                lineTo(9.0f, 20.0f)
                verticalLineToRelative(1f)
                close()
                moveTo(12.0f, 2f)
                curveTo(8.1f, 2f, 5.0f, 5.1f, 5.0f, 9.0f)
                curveToRelative(0f, 2.4f, 1.2f, 4.5f, 3.0f, 5.7f)
                lineTo(8.0f, 17.0f)
                curveToRelative(0f, 0.5f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.6f, 0f, 1f, -0.5f, 1f, -1.0f)
                verticalLineToRelative(-2.3f)
                curveToRelative(1.8f, -1.3f, 3.0f, -3.4f, 3.0f, -5.7f)
                curveToRelative(0f, -3.9f, -3.1f, -7.0f, -7.0f, -7.0f)
                close()
            }
        }
        return _lightbulb!!
    }

private var _lockOpen: ImageVector? = null
public val Icons.Filled.LockOpen: ImageVector
    get() {
        if (_lockOpen != null) return _lockOpen!!
        _lockOpen = icon("LockOpen") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 17.0f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2f)
                reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
                close()
                moveTo(18.0f, 8.0f)
                horizontalLineToRelative(-1.0f)
                lineTo(17.0f, 6.0f)
                curveToRelative(0f, -2.76f, -2.24f, -5.0f, -5.0f, -5.0f)
                reflectiveCurveTo(7.0f, 3.24f, 7.0f, 6.0f)
                horizontalLineToRelative(1.9f)
                curveToRelative(0f, -1.71f, 1.39f, -3.1f, 3.1f, -3.1f)
                curveToRelative(1.71f, 0f, 3.1f, 1.39f, 3.1f, 3.1f)
                verticalLineToRelative(2f)
                lineTo(6.0f, 8.0f)
                curveToRelative(-1.1f, 0f, -2.0f, 0.9f, -2.0f, 2f)
                verticalLineToRelative(10.0f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(12.0f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2.0f)
                lineTo(20.0f, 10.0f)
                curveToRelative(0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 20.0f)
                lineTo(6.0f, 20.0f)
                lineTo(6.0f, 10.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(10.0f)
                close()
            }
        }
        return _lockOpen!!
    }

private var _menuBook: ImageVector? = null
public val Icons.Filled.MenuBook: ImageVector
    get() {
        if (_menuBook != null) return _menuBook!!
        _menuBook = icon("MenuBook") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21.0f, 5.0f)
                curveToRelative(-1.11f, -0.35f, -2.33f, -0.5f, -3.5f, -0.5f)
                curveToRelative(-1.95f, 0f, -4.05f, 0.4f, -5.5f, 1.5f)
                curveToRelative(-1.45f, -1.1f, -3.55f, -1.5f, -5.5f, -1.5f)
                reflectiveCurveTo(2.45f, 4.9f, 1f, 6.0f)
                verticalLineToRelative(14.65f)
                curveToRelative(0f, 0.25f, 0.25f, 0.5f, 0.5f, 0.5f)
                curveToRelative(0.1f, 0f, 0.15f, -0.05f, 0.25f, -0.05f)
                curveTo(3.1f, 20.45f, 5.05f, 20.0f, 6.5f, 20.0f)
                curveToRelative(1.95f, 0f, 4.05f, 0.4f, 5.5f, 1.5f)
                curveToRelative(1.35f, -0.85f, 3.8f, -1.5f, 5.5f, -1.5f)
                curveToRelative(1.65f, 0f, 3.35f, 0.3f, 4.75f, 1.05f)
                curveToRelative(0.1f, 0.05f, 0.15f, 0.05f, 0.25f, 0.05f)
                curveToRelative(0.25f, 0f, 0.5f, -0.25f, 0.5f, -0.5f)
                verticalLineTo(6.0f)
                curveTo(22.4f, 5.55f, 21.75f, 5.25f, 21.0f, 5.0f)
                close()
                moveTo(21.0f, 18.5f)
                curveToRelative(-1.1f, -0.35f, -2.3f, -0.5f, -3.5f, -0.5f)
                curveToRelative(-1.7f, 0f, -4.15f, 0.65f, -5.5f, 1.5f)
                verticalLineTo(8.0f)
                curveToRelative(1.35f, -0.85f, 3.8f, -1.5f, 5.5f, -1.5f)
                curveToRelative(1.2f, 0f, 2.4f, 0.15f, 3.5f, 0.5f)
                verticalLineTo(18.5f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.5f, 10.5f)
                curveToRelative(0.88f, 0f, 1.73f, 0.09f, 2.5f, 0.26f)
                verticalLineTo(9.24f)
                curveTo(19.21f, 9.09f, 18.36f, 9.0f, 17.5f, 9.0f)
                curveToRelative(-1.7f, 0f, -3.24f, 0.29f, -4.5f, 0.83f)
                verticalLineToRelative(1.66f)
                curveTo(14.13f, 10.85f, 15.7f, 10.5f, 17.5f, 10.5f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(13.0f, 12.49f)
                verticalLineToRelative(1.66f)
                curveToRelative(1.13f, -0.64f, 2.7f, -0.99f, 4.5f, -0.99f)
                curveToRelative(0.88f, 0f, 1.73f, 0.09f, 2.5f, 0.26f)
                verticalLineTo(11.9f)
                curveToRelative(-0.79f, -0.15f, -1.64f, -0.24f, -2.5f, -0.24f)
                curveTo(15.8f, 11.66f, 14.26f, 11.96f, 13.0f, 12.49f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.5f, 14.33f)
                curveToRelative(-1.7f, 0f, -3.24f, 0.29f, -4.5f, 0.83f)
                verticalLineToRelative(1.66f)
                curveToRelative(1.13f, -0.64f, 2.7f, -0.99f, 4.5f, -0.99f)
                curveToRelative(0.88f, 0f, 1.73f, 0.09f, 2.5f, 0.26f)
                verticalLineToRelative(-1.52f)
                curveTo(19.21f, 14.41f, 18.36f, 14.33f, 17.5f, 14.33f)
                close()
            }
        }
        return _menuBook!!
    }

private var _ondemandVideo: ImageVector? = null
public val Icons.Filled.OndemandVideo: ImageVector
    get() {
        if (_ondemandVideo != null) return _ondemandVideo!!
        _ondemandVideo = icon("OndemandVideo") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21.0f, 3.0f)
                lineTo(3.0f, 3.0f)
                curveToRelative(-1.11f, 0f, -2.0f, 0.89f, -2.0f, 2f)
                verticalLineToRelative(12.0f)
                curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(5.0f)
                curveToRelative(1.1f, 0f, 1.99f, -0.9f, 1.99f, -2.0f)
                lineTo(23.0f, 5.0f)
                curveToRelative(0f, -1.11f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(21.0f, 17.0f)
                lineTo(3.0f, 17.0f)
                lineTo(3.0f, 5.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(16.0f, 11.0f)
                lineToRelative(-7.0f, 4.0f)
                lineTo(9.0f, 7.0f)
                close()
            }
        }
        return _ondemandVideo!!
    }

private var _school: ImageVector? = null
public val Icons.Filled.School: ImageVector
    get() {
        if (_school != null) return _school!!
        _school = icon("School") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.0f, 13.18f)
                verticalLineToRelative(4.0f)
                lineTo(12.0f, 21.0f)
                lineToRelative(7.0f, -3.82f)
                verticalLineToRelative(-4.0f)
                lineTo(12.0f, 17.0f)
                lineToRelative(-7.0f, -3.82f)
                close()
                moveTo(12.0f, 3.0f)
                lineTo(1f, 9.0f)
                lineToRelative(11.0f, 6.0f)
                lineToRelative(9.0f, -4.91f)
                verticalLineTo(17.0f)
                horizontalLineToRelative(2f)
                verticalLineTo(9.0f)
                lineTo(12.0f, 3.0f)
                close()
            }
        }
        return _school!!
    }

private var _security: ImageVector? = null
public val Icons.Filled.Security: ImageVector
    get() {
        if (_security != null) return _security!!
        _security = icon("Security") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 1f)
                lineTo(3.0f, 5.0f)
                verticalLineToRelative(6.0f)
                curveToRelative(0f, 5.55f, 3.84f, 10.74f, 9.0f, 12.0f)
                curveToRelative(5.16f, -1.26f, 9.0f, -6.45f, 9.0f, -12.0f)
                lineTo(21.0f, 5.0f)
                lineToRelative(-9.0f, -4.0f)
                close()
                moveTo(12.0f, 11.99f)
                horizontalLineToRelative(7.0f)
                curveToRelative(-0.53f, 4.12f, -3.28f, 7.79f, -7.0f, 8.94f)
                lineTo(12.0f, 12.0f)
                lineTo(5.0f, 12.0f)
                lineTo(5.0f, 6.3f)
                lineToRelative(7.0f, -3.11f)
                verticalLineToRelative(8.8f)
                close()
            }
        }
        return _security!!
    }

private var _stop: ImageVector? = null
public val Icons.Filled.Stop: ImageVector
    get() {
        if (_stop != null) return _stop!!
        _stop = icon("Stop") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 6.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(12.0f)
                horizontalLineTo(6.0f)
                close()
            }
        }
        return _stop!!
    }

private var _timer: ImageVector? = null
public val Icons.Filled.Timer: ImageVector
    get() {
        if (_timer != null) return _timer!!
        _timer = icon("Timer") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.0f, 1f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-6.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.03f, 7.39f)
                lineToRelative(1.42f, -1.42f)
                curveToRelative(-0.43f, -0.51f, -0.9f, -0.99f, -1.41f, -1.41f)
                lineToRelative(-1.42f, 1.42f)
                curveTo(16.07f, 4.74f, 14.12f, 4.0f, 12.0f, 4.0f)
                curveToRelative(-4.97f, 0f, -9.0f, 4.03f, -9.0f, 9.0f)
                curveToRelative(0f, 4.97f, 4.02f, 9.0f, 9.0f, 9.0f)
                reflectiveCurveToRelative(9.0f, -4.03f, 9.0f, -9.0f)
                curveTo(21.0f, 10.88f, 20.26f, 8.93f, 19.03f, 7.39f)
                close()
                moveTo(13.0f, 14.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(2f)
                verticalLineTo(14.0f)
                close()
            }
        }
        return _timer!!
    }

private var _tune: ImageVector? = null
public val Icons.Filled.Tune: ImageVector
    get() {
        if (_tune != null) return _tune!!
        _tune = icon("Tune") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 17.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(-2.0f)
                lineTo(3.0f, 17.0f)
                close()
                moveTo(3.0f, 5.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(10.0f)
                lineTo(13.0f, 5.0f)
                lineTo(3.0f, 5.0f)
                close()
                moveTo(13.0f, 21.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-8.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(2f)
                close()
                moveTo(7.0f, 9.0f)
                verticalLineToRelative(2f)
                lineTo(3.0f, 11.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                lineTo(9.0f, 9.0f)
                lineTo(7.0f, 9.0f)
                close()
                moveTo(21.0f, 13.0f)
                verticalLineToRelative(-2.0f)
                lineTo(11.0f, 11.0f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(10.0f)
                close()
                moveTo(15.0f, 9.0f)
                horizontalLineToRelative(2f)
                lineTo(17.0f, 7.0f)
                horizontalLineToRelative(4.0f)
                lineTo(21.0f, 5.0f)
                horizontalLineToRelative(-4.0f)
                lineTo(17.0f, 3.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(6.0f)
                close()
            }
        }
        return _tune!!
    }

private var _undo: ImageVector? = null
public val Icons.Filled.Undo: ImageVector
    get() {
        if (_undo != null) return _undo!!
        _undo = icon("Undo") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.5f, 8.0f)
                curveToRelative(-2.65f, 0f, -5.05f, 0.99f, -6.9f, 2.6f)
                lineTo(2f, 7.0f)
                verticalLineToRelative(9.0f)
                horizontalLineToRelative(9.0f)
                lineToRelative(-3.62f, -3.62f)
                curveToRelative(1.39f, -1.16f, 3.16f, -1.88f, 5.12f, -1.88f)
                curveToRelative(3.54f, 0f, 6.55f, 2.31f, 7.6f, 5.5f)
                lineToRelative(2.37f, -0.78f)
                curveTo(21.08f, 11.03f, 17.15f, 8.0f, 12.5f, 8.0f)
                close()
            }
        }
        return _undo!!
    }

private var _vibration: ImageVector? = null
public val Icons.Filled.Vibration: ImageVector
    get() {
        if (_vibration != null) return _vibration!!
        _vibration = icon("Vibration") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(0f, 15.0f)
                horizontalLineToRelative(2f)
                lineTo(2f, 9.0f)
                lineTo(0f, 9.0f)
                verticalLineToRelative(6.0f)
                close()
                moveTo(3.0f, 17.0f)
                horizontalLineToRelative(2f)
                lineTo(5.0f, 7.0f)
                lineTo(3.0f, 7.0f)
                verticalLineToRelative(10.0f)
                close()
                moveTo(22.0f, 9.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(2f)
                lineTo(24.0f, 9.0f)
                horizontalLineToRelative(-2.0f)
                close()
                moveTo(19.0f, 17.0f)
                horizontalLineToRelative(2f)
                lineTo(21.0f, 7.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(10.0f)
                close()
                moveTo(16.5f, 3.0f)
                horizontalLineToRelative(-9.0f)
                curveTo(6.67f, 3.0f, 6.0f, 3.67f, 6.0f, 4.5f)
                verticalLineToRelative(15.0f)
                curveToRelative(0f, 0.83f, 0.67f, 1.5f, 1.5f, 1.5f)
                horizontalLineToRelative(9.0f)
                curveToRelative(0.83f, 0f, 1.5f, -0.67f, 1.5f, -1.5f)
                verticalLineToRelative(-15.0f)
                curveToRelative(0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
                close()
                moveTo(16.0f, 19.0f)
                lineTo(8.0f, 19.0f)
                lineTo(8.0f, 5.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(14.0f)
                close()
            }
        }
        return _vibration!!
    }

private var _visibilityOff: ImageVector? = null
public val Icons.Filled.VisibilityOff: ImageVector
    get() {
        if (_visibilityOff != null) return _visibilityOff!!
        _visibilityOff = icon("VisibilityOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 7.0f)
                curveToRelative(2.76f, 0f, 5.0f, 2.24f, 5.0f, 5.0f)
                curveToRelative(0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
                lineToRelative(2.92f, 2.92f)
                curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
                curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                curveToRelative(-1.4f, 0f, -2.74f, 0.25f, -3.98f, 0.7f)
                lineToRelative(2.16f, 2.16f)
                curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
                close()
                moveTo(2f, 4.27f)
                lineToRelative(2.28f, 2.28f)
                lineToRelative(0.46f, 0.46f)
                curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1f, 12.0f)
                curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                curveToRelative(1.55f, 0f, 3.03f, -0.3f, 4.38f, -0.84f)
                lineToRelative(0.42f, 0.42f)
                lineTo(19.73f, 22.0f)
                lineTo(21.0f, 20.73f)
                lineTo(3.27f, 3.0f)
                lineTo(2f, 4.27f)
                close()
                moveTo(7.53f, 9.8f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                curveToRelative(0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
                curveToRelative(0.22f, 0f, 0.44f, -0.03f, 0.65f, -0.08f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
                curveToRelative(-2.76f, 0f, -5.0f, -2.24f, -5.0f, -5.0f)
                curveToRelative(0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
                close()
                moveTo(11.84f, 9.02f)
                lineToRelative(3.15f, 3.15f)
                lineToRelative(0.02f, -0.16f)
                curveToRelative(0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                lineToRelative(-0.17f, 0.01f)
                close()
            }
        }
        return _visibilityOff!!
    }

private var _volumeMute: ImageVector? = null
public val Icons.Filled.VolumeMute: ImageVector
    get() {
        if (_volumeMute != null) return _volumeMute!!
        _volumeMute = icon("VolumeMute") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.0f, 9.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(4.0f)
                lineToRelative(5.0f, 5.0f)
                verticalLineTo(4.0f)
                lineToRelative(-5.0f, 5.0f)
                horizontalLineTo(7.0f)
                close()
            }
        }
        return _volumeMute!!
    }

private var _volumeOff: ImageVector? = null
public val Icons.Filled.VolumeOff: ImageVector
    get() {
        if (_volumeOff != null) return _volumeOff!!
        _volumeOff = icon("VolumeOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.5f, 12.0f)
                curveToRelative(0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
                verticalLineToRelative(2.21f)
                lineToRelative(2.45f, 2.45f)
                curveToRelative(0.03f, -0.2f, 0.05f, -0.41f, 0.05f, -0.63f)
                close()
                moveTo(19.0f, 12.0f)
                curveToRelative(0f, 0.94f, -0.2f, 1.82f, -0.54f, 2.64f)
                lineToRelative(1.51f, 1.51f)
                curveTo(20.63f, 14.91f, 21.0f, 13.5f, 21.0f, 12.0f)
                curveToRelative(0f, -4.28f, -2.99f, -7.86f, -7.0f, -8.77f)
                verticalLineToRelative(2.06f)
                curveToRelative(2.89f, 0.86f, 5.0f, 3.54f, 5.0f, 6.71f)
                close()
                moveTo(4.27f, 3.0f)
                lineTo(3.0f, 4.27f)
                lineTo(7.73f, 9.0f)
                lineTo(3.0f, 9.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(4.0f)
                lineToRelative(5.0f, 5.0f)
                verticalLineToRelative(-6.73f)
                lineToRelative(4.25f, 4.25f)
                curveToRelative(-0.67f, 0.52f, -1.42f, 0.93f, -2.25f, 1.18f)
                verticalLineToRelative(2.06f)
                curveToRelative(1.38f, -0.31f, 2.63f, -0.95f, 3.69f, -1.81f)
                lineTo(19.73f, 21.0f)
                lineTo(21.0f, 19.73f)
                lineToRelative(-9.0f, -9.0f)
                lineTo(4.27f, 3.0f)
                close()
                moveTo(12.0f, 4.0f)
                lineTo(9.91f, 6.09f)
                lineTo(12.0f, 8.18f)
                lineTo(12.0f, 4.0f)
                close()
            }
        }
        return _volumeOff!!
    }

private var _volumeUp: ImageVector? = null
public val Icons.Filled.VolumeUp: ImageVector
    get() {
        if (_volumeUp != null) return _volumeUp!!
        _volumeUp = icon("VolumeUp") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 9.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(4.0f)
                lineToRelative(5.0f, 5.0f)
                lineTo(12.0f, 4.0f)
                lineTo(7.0f, 9.0f)
                lineTo(3.0f, 9.0f)
                close()
                moveTo(16.5f, 12.0f)
                curveToRelative(0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
                verticalLineToRelative(8.05f)
                curveToRelative(1.48f, -0.73f, 2.5f, -2.25f, 2.5f, -4.02f)
                close()
                moveTo(14.0f, 3.23f)
                verticalLineToRelative(2.06f)
                curveToRelative(2.89f, 0.86f, 5.0f, 3.54f, 5.0f, 6.71f)
                reflectiveCurveToRelative(-2.11f, 5.85f, -5.0f, 6.71f)
                verticalLineToRelative(2.06f)
                curveToRelative(4.01f, -0.91f, 7.0f, -4.49f, 7.0f, -8.77f)
                reflectiveCurveToRelative(-2.99f, -7.86f, -7.0f, -8.77f)
                close()
            }
        }
        return _volumeUp!!
    }
