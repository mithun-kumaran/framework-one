package org.example.app

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

/**
 * @property nearBackground A useful color to apply to a container that should differentiate itself from the background
 *   but just a little.
 */
class SitePalette(
    val nearBackground: Color,
    val cobweb: Color,
    val brand: Brand,
) {
    class Brand(
        val primary: Color = Color.rgb(0x1F2937),
        val accent: Color = Color.rgb(0xC59A3E),
    )
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(0xEEEAE5),
        cobweb = Color.rgb(0xD9D4CC),
        brand = SitePalette.Brand(
            primary = Color.rgb(0x1F2937),
            accent = Color.rgb(0xC59A3E),
        )
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(0x1A1C1E),
        cobweb = Color.rgb(0x2A2F36),
        brand = SitePalette.Brand(
            primary = Color.rgb(0xE5E7EB),
            accent = Color.rgb(0xC59A3E),
        )
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(0xF7F5F2)
    ctx.theme.palettes.light.color = Color.rgb(0x111827)
    ctx.theme.palettes.dark.background = Color.rgb(0x0B0B0D)
    ctx.theme.palettes.dark.color = Color.rgb(0xF9FAFB)
}
