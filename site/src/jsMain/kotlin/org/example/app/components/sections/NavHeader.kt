package org.example.app.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
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
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

val NavHeaderStyle = CssStyle.base {
    Modifier
        .position(Position.Relative)
        .zIndex(10)
        .fillMaxWidth()
        .maxWidth(104.cssRem)
        .alignSelf(AlignSelf.Center)
        .padding(topBottom = 1.1.cssRem, leftRight = 2.2.cssRem)
        .fontSize(0.76.cssRem)
}

val NavLinkStyle = CssStyle.base {
    Modifier
        .letterSpacing(0.06.cssRem)
        .fontWeight(FontWeight.Medium)
        .color(Color.rgba(255, 255, 255, 0.82f))
}

val NavLinksRowStyle = CssStyle.base {
    Modifier
        .flex(1)
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.SpaceEvenly)
        .alignItems(AlignItems.Center)
        .gap(0.cssRem)
}

val NavBrandStyle = CssStyle.base {
    Modifier
        .height(1.8.cssRem)
        .width(5.4.cssRem)
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
    Row(
        NavHeaderStyle.toModifier(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.padding(right = 1.2.cssRem), contentAlignment = Alignment.Center) {
            Img(
                src = "/full%20logo.svg",
                alt = "Aerova Elite",
                attrs = NavBrandStyle.toModifier().toAttrs()
            )
        }

        Row(
            NavLinksRowStyle.toModifier(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavLink("about", "About")
            NavLink("chauffeur", "Chauffeur")
            NavLink("fleet", "Fleet")
            Link("/book", "Book Now", modifier = NavLinkStyle.toModifier(), variant = UndecoratedLinkVariant.then(UncoloredLinkVariant))
            NavLink("vip-travel", "VIP Travel")
            NavLink("reviews", "Reviews")
            NavLink("faqs", "FAQs")
            NavLink("footer", "Contact")
        }
        Row(
            Modifier.gap(0.6.cssRem).justifyContent(JustifyContent.FlexEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier.gap(2.2.cssRem), verticalAlignment = Alignment.CenterVertically) {
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/instagram%20logo.svg",
                        alt = "Instagram",
                        attrs = Modifier.height(1.1.cssRem).width(1.1.cssRem).toAttrs()
                    )
                }
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/google%20icon.svg",
                        alt = "Google",
                        attrs = Modifier.height(1.1.cssRem).width(1.1.cssRem).toAttrs()
                    )
                }
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/whatsapp%20logo.svg",
                        alt = "WhatsApp",
                        attrs = Modifier.height(1.1.cssRem).width(1.1.cssRem).toAttrs()
                    )
                }
                A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("#") }) {
                    Img(
                        src = "/phone%20icon.svg",
                        alt = "Phone",
                        attrs = Modifier.height(1.1.cssRem).width(1.1.cssRem).toAttrs()
                    )
                }
            }
        }
    }
}
