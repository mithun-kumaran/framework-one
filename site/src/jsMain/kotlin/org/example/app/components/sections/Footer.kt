package org.example.app.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

val FooterStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(top = clamp(4.6.cssRem, 16.vh, 7.6.cssRem), bottom = 3.2.cssRem, leftRight = clamp(2.cssRem, 6.vw, 6.cssRem))
        .backgroundColor(Color.rgb(0x000000))
        .fontSize(0.9.cssRem)
        .color(Color.rgb(0xF2F2F2))
}

val FooterCenterStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .gap(1.1.cssRem)
        .textAlign(TextAlign.Center)
}

val FooterLogoStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.4.cssRem)
        .alignItems(AlignItems.Center)
        .fontSize(0.8.cssRem)
        .color(Color.rgba(255, 255, 255, 0.88f))
}

val FooterHeadlineStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.35.cssRem, 4.vw, 1.9.cssRem))
        .color(Colors.White)
        .margin(topBottom = 0.px)
}

val FooterButtonStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.5.cssRem, topBottom = 0.48.cssRem)
        .borderRadius(999.px)
        .backgroundColor(Colors.White)
        .color(Color.rgb(0x0B0B0B))
        .fontSize(0.68.cssRem)
        .fontWeight(FontWeight.Medium)
}

val FooterSocialRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.7.cssRem)
        .alignItems(AlignItems.Center)
}

val FooterBottomRowStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.SpaceBetween)
        .alignItems(AlignItems.Center)
        .flexWrap(FlexWrap.Wrap)
        .gap(1.4.cssRem)
        .padding(top = clamp(2.8.cssRem, 9.vh, 4.8.cssRem))
}

val FooterBottomTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.64.cssRem)
        .color(Color.rgba(255, 255, 255, 0.62f))
}

val FooterBottomLinksStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).gap(1.1.cssRem).alignItems(AlignItems.Center)
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Div(FooterStyle.toModifier().then(modifier).id("footer").toAttrs {
        style {
            property("background", "radial-gradient(circle at 50% 40%, rgba(40, 44, 50, 0.55), rgba(0, 0, 0, 0.95) 65%)")
        }
    }) {
        Column(Modifier.fillMaxWidth().gap(1.6.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(FooterCenterStyle.toModifier(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(FooterLogoStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    Img(src = "/logo.svg", alt = "Aerova Elite", attrs = Modifier.size(1.05.cssRem).toAttrs())
                    Text("Aerova Elite")
                }
                P(FooterHeadlineStyle.toModifier().toAttrs()) { Text("Travel Without Compromise") }
                A(attrs = FooterButtonStyle.toModifier().toAttrs {
                    href("/book")
                    style { property("text-decoration", "none") }
                }) { Text("Book a ride") }
            }
            Row(FooterBottomRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                P(FooterBottomTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("© 2026 Aerova Elite Technologies Inc.") }
                Row(FooterBottomLinksStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    A(attrs = FooterBottomTextStyle.toModifier().toAttrs {
                        href("/terms")
                        style { property("text-decoration", "none") }
                    }) { Text("Terms & Conditions") }
                    A(attrs = FooterBottomTextStyle.toModifier().toAttrs {
                        href("/privacy")
                        style { property("text-decoration", "none") }
                    }) { Text("Privacy Policy") }
                    A(attrs = FooterBottomTextStyle.toModifier().toAttrs {
                        href("/block")
                        style { property("text-decoration", "none") }
                    }) { Text("Block") }
                }
                Row(FooterSocialRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://instagram.com") }) {
                        Img(src = "/instagram%20logo.svg", alt = "Instagram", attrs = Modifier.height(1.cssRem).width(1.cssRem).toAttrs())
                    }
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://wa.me") }) {
                        Img(src = "/whatsapp%20logo.svg", alt = "WhatsApp", attrs = Modifier.height(1.cssRem).width(1.cssRem).toAttrs())
                    }
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://tiktok.com") }) {
                        Img(src = "/tiktok%20logo.svg", alt = "TikTok", attrs = Modifier.height(1.cssRem).width(1.cssRem).toAttrs())
                    }
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("tel:+440000000000") }) {
                        Img(src = "/phone%20icon.svg", alt = "Phone", attrs = Modifier.height(1.cssRem).width(1.cssRem).toAttrs())
                    }
                }
            }
        }
    }
}
