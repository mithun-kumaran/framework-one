package org.example.app.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Img
import org.w3c.dom.events.Event

val NavHeaderStyle = CssStyle.base {
    Modifier
        .position(Position.Sticky)
        .top(0.px)
        .zIndex(10)
        .fillMaxWidth()
        .fontSize(0.95.cssRem)
}

val NavLinkStyle = CssStyle.base {
    Modifier
        .letterSpacing(0.14.cssRem)
        .fontWeight(FontWeight.Medium)
        .color(Color.rgb(0xEDEDED))
}

private fun smoothScrollTo(id: String) {
    val target = document.getElementById(id) ?: return
    target.asDynamic().scrollIntoView(js("({ behavior: 'smooth', block: 'start' })"))
    window.location.hash = id
}

@Composable
private fun NavLink(id: String, text: String) {
    Link(
        "#$id",
        text,
        modifier = NavLinkStyle.toModifier().onClick {
            it.preventDefault()
            smoothScrollTo(id)
        },
        variant = UndecoratedLinkVariant.then(UncoloredLinkVariant)
    )
}

@Composable
fun NavHeader() {
    var isScrolled by remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            isScrolled = window.scrollY > 60.0
        }
        isScrolled = window.scrollY > 60.0
        window.addEventListener("scroll", listener)
        onDispose { window.removeEventListener("scroll", listener) }
    }

    val stateModifier = if (isScrolled) {
        Modifier
            .width(clamp(70.percent, 92.vw, 88.percent))
            .alignSelf(AlignSelf.Center)
            .top(0.9.cssRem)
            .padding(
                leftRight = clamp(2.1.cssRem, 5.vw, 3.8.cssRem),
                topBottom = clamp(0.75.cssRem, 2.vw, 1.1.cssRem)
            )
            .borderRadius(2.6.cssRem)
            .backgroundColor(Color.rgba(12, 12, 12, 0.75f))
            .boxShadow(offsetX = 0.px, offsetY = 12.px, blurRadius = 32.px, color = Color.rgba(0, 0, 0, 0.45f))
            .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.08f))
    } else {
        Modifier
            .fillMaxWidth()
            .top(0.px)
            .padding(
                leftRight = clamp(2.6.cssRem, 7.vw, 5.2.cssRem),
                topBottom = clamp(1.4.cssRem, 3.vw, 1.9.cssRem)
            )
            .borderRadius(1.4.cssRem)
            .backgroundColor(Color.rgb(0x0B0B0B))
            .borderBottom(1.px, LineStyle.Solid, Color.rgb(0x1F1F1F))
    }

    Row(
        NavHeaderStyle.toModifier()
            .then(stateModifier)
            .transition {
                property("all")
                duration(260.ms)
                timingFunction(AnimationTimingFunction.Ease)
            }
            .gap(clamp(4.2.cssRem, 9.vw, 8.6.cssRem)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier.gap(clamp(3.4.cssRem, 7.8.vw, 6.6.cssRem)).flex(1).justifyContent(JustifyContent.FlexEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavLink("about-us", "About Us")
            NavLink("services", "Services")
            NavLink("gallery", "Gallery")
        }

        Box(Modifier.padding(leftRight = clamp(0.8.cssRem, 2.5.vw, 1.6.cssRem)), contentAlignment = Alignment.Center) {
            Img(
                src = "/main%20logo.svg",
                alt = "Peaky Blades logo",
                attrs = Modifier.height(clamp(2.8.cssRem, 4.5.vw, 3.8.cssRem)).toAttrs()
            )
        }

        Row(
            Modifier.gap(clamp(3.4.cssRem, 7.8.vw, 6.6.cssRem)).flex(1).justifyContent(JustifyContent.FlexStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavLink("footer", "Contact")
            Link(
                "/book",
                "Book Now",
                modifier = NavLinkStyle.toModifier(),
                variant = UndecoratedLinkVariant.then(UncoloredLinkVariant)
            )
            Row(Modifier.gap(0.7.cssRem), verticalAlignment = Alignment.CenterVertically) {
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/instagram%20logo.svg",
                        alt = "Instagram",
                        attrs = Modifier.height(1.5.cssRem).width(1.5.cssRem).toAttrs()
                    )
                }
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/whatsapp%20logo.svg",
                        alt = "WhatsApp",
                        attrs = Modifier.height(1.5.cssRem).width(1.5.cssRem).toAttrs()
                    )
                }
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/tiktok%20logo.svg",
                        alt = "TikTok",
                        attrs = Modifier.height(1.5.cssRem).width(1.5.cssRem).toAttrs()
                    )
                }
            }
        }
    }
}
