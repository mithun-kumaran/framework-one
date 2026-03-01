package org.example.app.components.sections

import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

val FooterStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 3.5.cssRem, leftRight = clamp(2.cssRem, 6.vw, 6.cssRem))
        .backgroundColor(Color.rgb(0x0B0B0B))
        .fontSize(0.9.cssRem)
        .color(Color.rgb(0xF2F2F2))
}

val FooterTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.2.cssRem, 2.5.vw, 1.6.cssRem))
        .fontWeight(500)
        .letterSpacing(0.08.cssRem)
        .textAlign(TextAlign.Center)
}

val FooterFormRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(1.cssRem)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .flexWrap(FlexWrap.Wrap)
}

val FooterInputStyle = CssStyle.base {
    Modifier
        .width(clamp(16.cssRem, 32.vw, 22.cssRem))
        .height(2.6.cssRem)
        .padding(leftRight = 1.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0x2A2A2A))
        .borderRadius(0.6.cssRem)
        .backgroundColor(Color.rgb(0x0F0F0F))
        .color(Color.rgb(0xF2F2F2))
        .fontSize(0.85.cssRem)
}

val FooterButtonStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.5.cssRem, topBottom = 0.65.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xC59A3E))
        .borderRadius(0.6.cssRem)
        .backgroundColor(Colors.Transparent)
        .color(Color.rgb(0xF2F2F2))
        .fontWeight(500)
        .fontSize(0.75.cssRem)
        .letterSpacing(0.2.cssRem)
}

val FooterSocialRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.9.cssRem)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
}

val FooterInfoRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(clamp(2.cssRem, 6.vw, 6.cssRem))
        .justifyContent(JustifyContent.SpaceBetween)
        .alignItems(AlignItems.Center)
        .flexWrap(FlexWrap.Wrap)
}

val FooterInfoLabelStyle = CssStyle.base {
    Modifier
        .fontSize(0.65.cssRem)
        .letterSpacing(0.16.cssRem)
        .fontWeight(600)
        .color(Color.rgb(0xF2F2F2))
}

val FooterInfoValueStyle = CssStyle.base {
    Modifier
        .fontSize(0.85.cssRem)
        .color(Color.rgb(0xB8B8B8))
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Div(FooterStyle.toModifier().then(modifier).id("footer").toAttrs()) {
        Column(Modifier.fillMaxWidth().gap(2.2.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(Modifier.fillMaxWidth().gap(1.2.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
                P(FooterTitleStyle.toAttrs()) { Text("Stay Knowledgeable. Subscribe below.") }
                Div(FooterFormRowStyle.toModifier().toAttrs()) {
                    Input(
                        type = InputType.Email,
                        attrs = FooterInputStyle.toModifier().toAttrs {
                            placeholder("Email Address")
                        }
                    )
                    Button(attrs = FooterButtonStyle.toModifier().toAttrs()) {
                        Text("SUBSCRIBE")
                    }
                }
                Div(FooterSocialRowStyle.toModifier().toAttrs()) {
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://instagram.com") }) {
                        Img(src = "/instagram%20logo.svg", alt = "Instagram", attrs = Modifier.height(1.3.cssRem).width(1.3.cssRem).toAttrs())
                    }
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://wa.me") }) {
                        Img(src = "/whatsapp%20logo.svg", alt = "WhatsApp", attrs = Modifier.height(1.3.cssRem).width(1.3.cssRem).toAttrs())
                    }
                    A(attrs = Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).toAttrs { href("https://tiktok.com") }) {
                        Img(src = "/tiktok%20logo.svg", alt = "TikTok", attrs = Modifier.height(1.3.cssRem).width(1.3.cssRem).toAttrs())
                    }
                }
            }

            Div(FooterInfoRowStyle.toModifier().toAttrs()) {
                FooterInfoItem("/phone%20icon.svg", "MOBILE PHONE", "+88005553535")
                FooterInfoItem("/location%20icon.svg", "LOCATION", "Tarasa Shevchenko Str. 21")
                FooterInfoItem("/clock%20icon.svg", "OPEN MONDAY-SUNDAY", "09:00 - 18:00")
            }
        }
    }
}

@Composable
private fun FooterInfoItem(icon: String, label: String, value: String) {
    Row(Modifier.gap(0.8.cssRem).alignItems(AlignItems.Center)) {
        Img(src = icon, alt = label, attrs = Modifier.height(1.7.cssRem).width(1.7.cssRem).toAttrs())
        Column(Modifier.gap(0.05.cssRem)) {
            P(FooterInfoLabelStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(label) }
            P(FooterInfoValueStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(value) }
        }
    }
}
