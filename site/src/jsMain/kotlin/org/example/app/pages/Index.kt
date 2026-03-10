package org.example.app.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.value
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Ul
import kotlinx.browser.document
import org.example.app.components.layouts.PageLayoutData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLSelectElement

// Container that has a tagline and grid on desktop, and just the tagline on mobile
val HeroContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .gap(0.5.cssRem)
            .padding(top = clamp(6.4.cssRem, 18.vh, 12.cssRem))
    }
    Breakpoint.MD { Modifier.margin { top(0.vh) } }
}

// A demo grid that appears on the homepage because it looks good
val HeroGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .gap(clamp(0.25.cssRem, 1.2.vw, 0.6.cssRem))
            .alignItems(AlignItems.Center)
    }
    Breakpoint.MD {
        Modifier.alignItems(AlignItems.Center)
    }
}

val EyebrowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.InlineBlock)
        .padding(leftRight = 0.9.cssRem, topBottom = 0.22.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
        .borderRadius(2.5.cssRem)
        .backgroundColor(Color.rgba(255, 255, 255, 0.07f))
        .color(Color.rgba(255, 255, 255, 0.88f))
        .fontSize(0.6.cssRem)
        .letterSpacing(0.08.cssRem)
}

val TitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.1.cssRem, 5.4.vw, 3.1.cssRem))
        .fontWeight(FontWeight.ExtraBold)
        .textAlign(TextAlign.Center)
        .letterSpacing(0.08.cssRem)
        .color(Colors.White)
        .margin(topBottom = 0.px)
}

val HeroSubtextStyle = CssStyle.base {
    Modifier
        .fontSize(0.72.cssRem)
        .color(Color.rgba(255, 255, 255, 0.7f))
        .textAlign(TextAlign.Center)
        .margin(topBottom = 0.px)
}

val HeroButtonsRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.4.cssRem)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .margin(top = 0.16.cssRem)
}

val BookButtonStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.White)
        .color(Color.rgb(0x0B0B0D))
        .borderRadius(2.cssRem)
        .padding(leftRight = 1.3.cssRem, topBottom = 0.65.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val HeroPillContainerStyle = CssStyle.base {
    Modifier
        .borderRadius(2.4.cssRem)
        .padding(leftRight = 0.6.cssRem, topBottom = 0.28.cssRem)
        .backgroundColor(Color.rgba(0, 0, 0, 0.7f))
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.12f))
        .display(DisplayStyle.Flex)
        .gap(0.6.cssRem)
        .alignItems(AlignItems.Center)
        .boxShadow(offsetX = 0.px, offsetY = 14.px, blurRadius = 34.px, color = Color.rgba(0, 0, 0, 0.55f))
}

val HeroPillTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.68.cssRem)
        .color(Color.rgba(255, 255, 255, 0.86f))
        .padding(leftRight = 0.5.cssRem, topBottom = 0.16.cssRem)
}

val HeroPillButtonStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.White)
        .color(Color.rgb(0x0B0B0D))
        .borderRadius(2.cssRem)
        .padding(leftRight = 1.05.cssRem, topBottom = 0.4.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.7.cssRem)
        .boxShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 16.px, color = Color.rgba(255, 255, 255, 0.35f))
}

val AboutSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(100.vh)
        .minHeight(100.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .gap(clamp(1.2.cssRem, 3.vh, 2.1.cssRem))
        .padding(topBottom = clamp(3.4.cssRem, 8.vh, 5.2.cssRem))
}

val AboutMapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(98.cssRem)
        .minHeight(clamp(32.cssRem, 56.vh, 46.cssRem))
        .margin(top = clamp(3.4.cssRem, 7.vh, 5.2.cssRem))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.FlexStart)
        .backgroundColor(Colors.Transparent)
}

val AboutHeadingStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.2.cssRem, 5.6.vw, 3.2.cssRem))
        .fontWeight(FontWeight.Bold)
        .color(Colors.White)
        .textAlign(TextAlign.Center)
        .lineHeight(1.2)
        .margin(bottom = 0.05.cssRem)
}

val AboutHeadingStrongStyle = CssStyle.base {
    Modifier
        .fontWeight(FontWeight.Bold)
        .color(Colors.White)
}

val AboutHeadingAccentStyle = CssStyle.base {
    Modifier.color(Color.rgb(0x595959))
}

val AboutSubtextStyle = CssStyle.base {
    Modifier
        .fontSize(1.02.cssRem)
        .color(Color.rgba(255, 255, 255, 0.7f))
        .textAlign(TextAlign.Center)
        .maxWidth(38.cssRem)
        .margin(top = 0.px)
        .margin(bottom = 0.px)
}

val FleetSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(100.vh)
        .minHeight(100.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .gap(clamp(0.7.cssRem, 2.4.vh, 1.4.cssRem))
        .padding(topBottom = clamp(3.4.cssRem, 8.vh, 5.2.cssRem))
}

val FleetTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.2.cssRem, 5.6.vw, 3.2.cssRem))
        .fontWeight(FontWeight.Bold)
        .color(Colors.White)
        .textAlign(TextAlign.Center)
        .margin(bottom = 0.05.cssRem)
}

val FleetTitleAccentStyle = CssStyle.base {
    Modifier.color(Color.rgb(0x595959))
}

val FleetSubtextStyle = CssStyle.base {
    Modifier
        .fontSize(1.02.cssRem)
        .color(Color.rgba(255, 255, 255, 0.7f))
        .textAlign(TextAlign.Center)
        .margin(top = 0.px)
        .margin(bottom = 0.px)
}

val FleetHeaderWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(102.cssRem)
        .position(Position.Relative)
}

val FleetImageWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(104.cssRem)
        .minHeight(clamp(26.cssRem, 58.vh, 42.cssRem))
        .position(Position.Relative)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .padding(top = clamp(1.2.cssRem, 4.vh, 2.6.cssRem))
}

val ChauffeurSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(100.vh)
        .minHeight(100.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .gap(clamp(1.2.cssRem, 3.vh, 2.2.cssRem))
        .padding(topBottom = clamp(3.4.cssRem, 8.vh, 5.2.cssRem))
}

val ChauffeurLuggageImageStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(clamp(46.cssRem, 88.vw, 78.cssRem))
        .margin(top = clamp(3.6.cssRem, 9.vh, 7.4.cssRem))
}

val FleetCarFrameStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(110.cssRem)
        .height(clamp(26.cssRem, 48.vh, 38.cssRem))
        .position(Position.Relative)
        .transform { translateY((-2.2).cssRem) }
}

val FleetArrowButtonStyle = CssStyle.base {
    Modifier
        .size(3.6.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .color(Colors.White)
        .cursor(Cursor.Pointer)
        .opacity(1)
        .backgroundColor(Colors.Transparent)
        .border(0.px, LineStyle.None, Colors.Transparent)
}

val FleetStatsPanelStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(clamp((-0.6).cssRem, (-1.2).vh, (-0.2).cssRem))
        .right(clamp((-3.2).cssRem, (-4.2).vw, (-1.6).cssRem))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.6.cssRem)
        .padding(1.15.cssRem)
        .maxWidth(34.cssRem)
        .zIndex(6)
}

val VipTravelSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(100.vh)
        .minHeight(100.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .position(Position.Relative)
        .padding(topBottom = clamp(3.4.cssRem, 8.vh, 5.2.cssRem))
}

val VipStatsPanelStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .bottom(clamp(2.6.cssRem, 6.vh, 4.2.cssRem))
        .right(clamp(2.6.cssRem, 6.vw, 4.6.cssRem))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.6.cssRem)
        .padding(1.15.cssRem)
        .maxWidth(34.cssRem)
        .zIndex(2)
}

val VipStatsTitleStyle = CssStyle.base {
    Modifier
        .fontSize(0.82.cssRem)
        .letterSpacing(0.22.cssRem)
        .color(Colors.White)
        .margin(left = 0.95.cssRem, bottom = 0.6.cssRem)
}

val FleetStatGridStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Grid)
        .gap(0.px)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .alignItems(AlignItems.Stretch)
}

val FleetStatCellStyle = CssStyle.base {
    Modifier
        .padding(1.1.cssRem)
        .display(DisplayStyle.Flex)
        .gap(0.7.cssRem)
        .alignItems(AlignItems.FlexStart)
        .color(Colors.White)
}

val FleetCtaButtonStyle = CssStyle.base {
    Modifier
        .margin(top = clamp(6.2.cssRem, 12.vh, 9.2.cssRem))
        .position(Position.Relative)
        .zIndex(2)
}

val FleetStatCellRightStyle = CssStyle.base {
    Modifier.borderLeft(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.16f))
}

val FleetStatCellBottomStyle = CssStyle.base {
    Modifier.borderTop(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.16f))
}

val FleetStatCellBottomRightStyle = CssStyle.base {
    Modifier
        .borderLeft(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.16f))
        .borderTop(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.16f))
}

val FleetStatTitleStyle = CssStyle.base {
    Modifier
        .fontSize(0.82.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .color(Colors.White)
}

val FleetStatBodyStyle = CssStyle.base {
    Modifier.color(Color.rgba(255, 255, 255, 0.7f)).fontSize(0.68.cssRem)
}

val FleetStatNumberStyle = CssStyle.base {
    Modifier.color(Color.rgb(0xE7C59A))
}

val SecondaryButtonStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.Transparent)
        .color(Colors.White)
        .borderRadius(2.cssRem)
        .padding(leftRight = 1.2.cssRem, topBottom = 0.6.cssRem)
        .fontWeight(FontWeight.Medium)
}

val SearchPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(68.cssRem)
        .padding(1.35.cssRem)
        .borderRadius(2.6.cssRem)
        .backgroundColor(Color.rgba(10, 10, 10, 0.82f))
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.06f))
        .boxShadow(offsetX = 0.px, offsetY = 18.px, blurRadius = 42.px, color = Color.rgba(0, 0, 0, 0.65f))
        .margin(top = clamp(15.6.cssRem, 36.vh, 22.cssRem), bottom = clamp(3.6.cssRem, 11.vh, 7.5.cssRem))
}

val SearchTrackStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(0.8.cssRem)
        .margin(bottom = 0.8.cssRem)
        .padding(leftRight = 0.4.cssRem)
}

val SearchTrackLineStyle = CssStyle.base {
    Modifier
        .flex(1)
        .height(1.px)
        .backgroundColor(Color.rgba(255, 255, 255, 0.28f))
}

val SectionDividerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(1.px)
        .margin(topBottom = clamp(4.2.cssRem, 10.vh, 7.2.cssRem))
        .backgroundColor(Color.rgba(255, 255, 255, 0.18f))
}

val SearchRowStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).gap(1.1.cssRem).alignItems(AlignItems.Center).flexWrap(FlexWrap.Nowrap)
}

val SearchFieldWrapStyle = CssStyle.base {
    Modifier.position(Position.Relative).flex(1).minWidth(12.cssRem)
}

val SearchFieldStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(leftRight = 1.15.cssRem, topBottom = 0.8.cssRem)
        .borderRadius(2.2.cssRem)
        .backgroundColor(Color.rgba(255, 255, 255, 0.12f))
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.12f))
        .display(DisplayStyle.Flex)
        .gap(0.7.cssRem)
        .alignItems(AlignItems.Center)
}

val SearchInputStyle = CssStyle.base {
    Modifier
        .flex(1)
        .backgroundColor(Colors.Transparent)
        .color(Colors.White)
        .fontSize(0.78.cssRem)
}

val SearchDropdownStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(100.percent)
        .left(0.px)
        .right(0.px)
        .margin(top = 0.5.cssRem)
        .backgroundColor(Color.rgba(12, 12, 12, 0.92f))
        .borderRadius(1.2.cssRem)
        .padding(topBottom = 0.4.cssRem)
        .boxShadow(offsetX = 0.px, offsetY = 16.px, blurRadius = 30.px, color = Color.rgba(0, 0, 0, 0.25f))
        .zIndex(5)
        .maxHeight(18.cssRem)
        .overflow(Overflow.Auto)
}

val SearchDropdownItemStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.cssRem, topBottom = 0.7.cssRem)
        .display(DisplayStyle.Flex)
        .gap(0.8.cssRem)
        .alignItems(AlignItems.Center)
        .cursor(Cursor.Pointer)
}

val SearchDropdownTitleStyle = CssStyle.base {
    Modifier.color(Colors.White).fontWeight(FontWeight.SemiBold).fontSize(0.76.cssRem)
}

val SearchDropdownSubtitleStyle = CssStyle.base {
    Modifier.color(Color.rgba(255, 255, 255, 0.65f)).fontSize(0.66.cssRem)
}

val SearchSelectStyle = CssStyle.base {
    Modifier
        .flex(1)
        .backgroundColor(Colors.Transparent)
        .color(Color.rgb(0x0B0B0D))
        .border(0.px, LineStyle.None, Colors.Transparent)
        .fontSize(0.78.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val SearchTimeTextStyle = CssStyle.base {
    Modifier
        .flex(1)
        .color(Color.rgb(0x0B0B0D))
        .fontSize(0.78.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val SearchButtonStyle = CssStyle.base {
    Modifier
        .size(3.2.cssRem)
        .borderRadius(50.percent)
        .backgroundColor(Colors.White)
        .color(Color.rgb(0x0B0B0D))
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .boxShadow(offsetX = 0.px, offsetY = 12.px, blurRadius = 24.px, color = Color.rgba(0, 0, 0, 0.5f))
}

val SearchTimeFieldStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.15.cssRem, topBottom = 0.8.cssRem)
        .borderRadius(2.2.cssRem)
        .backgroundColor(Colors.White)
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.65f))
        .display(DisplayStyle.Flex)
        .gap(0.7.cssRem)
        .alignItems(AlignItems.Center)
        .cursor(Cursor.Pointer)
}

val SearchTimeMenuStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(100.percent)
        .right(0.px)
        .margin(top = 0.5.cssRem)
        .backgroundColor(Colors.White)
        .borderRadius(1.1.cssRem)
        .padding(0.35.cssRem)
        .boxShadow(offsetX = 0.px, offsetY = 16.px, blurRadius = 30.px, color = Color.rgba(0, 0, 0, 0.2f))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.25.cssRem)
        .zIndex(7)
}

val SearchTimeOptionStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 0.9.cssRem, topBottom = 0.55.cssRem)
        .borderRadius(0.9.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(0.7.cssRem)
        .cursor(Cursor.Pointer)
        .fontSize(0.74.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .color(Color.rgb(0x0B0B0D))
}

val SearchTimeRadioStyle = CssStyle.base {
    Modifier
        .size(0.7.cssRem)
        .borderRadius(50.percent)
        .border(2.px, LineStyle.Solid, Color.rgb(0x0B0B0D))
}

val ServicesPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.Transparent)
        .padding(leftRight = 0.cssRem, topBottom = 0.cssRem)
        .color(Colors.White)
        .width(clamp(26.cssRem, 34.vw, 36.cssRem))
        .margin(bottom = 0.8.cssRem)
}

val ServiceRowStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 1.25.cssRem)
        .borderBottom(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.22f))
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.SpaceBetween)
        .alignItems(AlignItems.Center)
}

val ServiceNameStyle = CssStyle.base {
    Modifier.fontSize(0.95.cssRem).letterSpacing(0.08.cssRem)
}

val ServicePriceStyle = CssStyle.base {
    Modifier.fontSize(0.95.cssRem).color(Color.rgba(255, 255, 255, 0.85f))
        .transition {
            property("opacity")
            property("transform")
            duration(200.ms)
            timingFunction(AnimationTimingFunction.Ease)
        }
}

val ReviewMarqueeViewportStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .overflow(Overflow.Hidden)
        .borderLeft(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
        .borderRight(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
}

val ReviewMarqueeTrackStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Stretch)
        .flexWrap(FlexWrap.Nowrap)
}

val ReviewStripCardStyle = CssStyle.base {
    Modifier
        .width(clamp(13.cssRem, 17.vw, 17.cssRem))
        .height(8.4.cssRem)
        .padding(0.9.cssRem)
        .borderRadius(0.px)
        .backgroundColor(Colors.Transparent)
        .color(Colors.White)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.45.cssRem)
        .borderTop(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
        .borderBottom(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
        .borderRight(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f))
}

val ReviewStripNameStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.SemiBold).fontSize(0.76.cssRem).color(Colors.White)
}

val ReviewStripMetaStyle = CssStyle.base {
    Modifier.fontSize(0.62.cssRem).color(Color.rgba(255, 255, 255, 0.6f))
}

val ReviewStripTextStyle = CssStyle.base {
    Modifier.fontSize(0.66.cssRem).color(Color.rgba(255, 255, 255, 0.72f)).lineHeight(1.35)
}

val ReviewGridSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .gap(clamp(0.7.cssRem, 2.2.vh, 1.4.cssRem))
        .margin(top = clamp(7.cssRem, 14.vh, 11.cssRem))
        .margin(bottom = clamp(6.cssRem, 14.vh, 10.cssRem))
}

val ReviewGridWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(64.cssRem)
}

val ReviewGridCardStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .minHeight(10.2.cssRem)
        .padding(1.05.cssRem)
        .borderRadius(0.9.cssRem)
        .backgroundColor(Color.rgb(0x141414))
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.08f))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.5.cssRem)
        .color(Colors.White)
        .margin(bottom = clamp(0.8.cssRem, 2.vh, 1.1.cssRem))
        .boxShadow(offsetX = 0.px, offsetY = 6.px, blurRadius = 16.px, color = Color.rgba(0, 0, 0, 0.3f))
}

val ReviewGridStarsStyle = CssStyle.base {
    Modifier.fontSize(0.62.cssRem).letterSpacing(0.12.cssRem).color(Color.rgb(0xF5C542))
}

val ReviewGridNameStyle = CssStyle.base {
    Modifier.fontSize(0.72.cssRem).fontWeight(FontWeight.SemiBold).color(Colors.White)
}

val ReviewGridRoleStyle = CssStyle.base {
    Modifier.fontSize(0.6.cssRem).color(Color.rgba(255, 255, 255, 0.52f))
}

val ReviewGridTextStyle = CssStyle.base {
    Modifier.fontSize(0.63.cssRem).color(Color.rgba(255, 255, 255, 0.72f)).lineHeight(1.5)
}

val ReviewGridFeaturedCardStyle = CssStyle.base {
    Modifier.minHeight(14.8.cssRem)
}

val ReviewGridPhotoStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(8.4.cssRem)
        .borderRadius(0.7.cssRem)
        .backgroundColor(Color.rgba(0, 0, 0, 0.35f))
}

val TinyReviewSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .gap(1.8.cssRem)
        .margin(top = clamp(2.6.cssRem, 6.vh, 4.4.cssRem))
        .minHeight(clamp(16.cssRem, 30.vh, 22.cssRem))
        .padding(topBottom = clamp(3.2.cssRem, 9.vh, 5.6.cssRem))
}

val TinyReviewLineStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(1.px)
        .backgroundColor(Color.rgba(255, 255, 255, 0.12f))
        .margin(leftRight = (-2).cssRem)
}

val TinyReviewCardStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(34.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .textAlign(TextAlign.Center)
        .color(Colors.White)
}

val TinyReviewTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.76.cssRem)
        .color(Color.rgba(255, 255, 255, 0.75f))
        .lineHeight(1.5)
}

val TinyReviewStarsStyle = CssStyle.base {
    Modifier
        .fontSize(0.62.cssRem)
        .letterSpacing(0.12.cssRem)
        .color(Color.rgb(0xF5C542))
}

val TinyReviewNameStyle = CssStyle.base {
    Modifier.fontSize(0.78.cssRem).fontWeight(FontWeight.SemiBold).color(Colors.White)
}

val TinyReviewHandleStyle = CssStyle.base {
    Modifier.fontSize(0.62.cssRem).color(Color.rgba(255, 255, 255, 0.55f))
}

val FaqSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(100.vh)
        .minHeight(100.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .gap(clamp(0.7.cssRem, 2.2.vh, 1.4.cssRem))
        .padding(topBottom = clamp(3.4.cssRem, 8.vh, 5.2.cssRem))
}

val FaqWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(clamp(44.cssRem, 70.vw, 56.cssRem))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.px)
}

val FaqListStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderTop(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.12f))
}

val FaqItemStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderBottom(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.12f))
}

val FaqQuestionRowStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .padding(topBottom = 0.95.cssRem)
        .cursor(Cursor.Pointer)
}

val FaqQuestionTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(0.84.cssRem, 1.6.vw, 0.98.cssRem))
        .fontWeight(FontWeight.Medium)
        .color(Colors.White)
}

val FaqIconStyle = CssStyle.base {
    Modifier
        .fontSize(1.2.cssRem)
        .color(Colors.White)
        .transition {
            property("transform")
            duration(200.ms)
            timingFunction(AnimationTimingFunction.Ease)
        }
}

val FaqAnswerWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxHeight(0.cssRem)
        .opacity(0.0)
        .overflow(Overflow.Hidden)
        .transition {
            property("max-height")
            property("opacity")
            duration(240.ms)
            timingFunction(AnimationTimingFunction.Ease)
        }
}

val FaqAnswerOpenStyle = CssStyle.base {
    Modifier
        .maxHeight(20.cssRem)
        .opacity(1.0)
        .padding(bottom = 0.9.cssRem)
}

val FaqAnswerTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.72.cssRem)
        .color(Color.rgba(255, 255, 255, 0.68f))
        .lineHeight(1.55)
}

val SeoSectionStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .left((-10000).px)
        .top((-10000).px)
        .width(1.px)
        .height(1.px)
        .overflow(Overflow.Hidden)
}

val SeoContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(clamp(46.cssRem, 72.vw, 62.cssRem))
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.85.cssRem)
}

val SeoHeadingStyle = CssStyle.base {
    Modifier
        .fontSize(1.05.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .color(Colors.White)
        .margin(top = 0.6.cssRem, bottom = 0.px)
}

val SeoParagraphStyle = CssStyle.base {
    Modifier
        .fontSize(0.78.cssRem)
        .color(Color.rgba(255, 255, 255, 0.74f))
        .lineHeight(1.6)
        .margin(bottom = 0.px)
}

val SeoListStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.35.cssRem)
        .padding(left = 1.2.cssRem)
        .margin(topBottom = 0.px)
}

val SeoListItemStyle = CssStyle.base {
    Modifier
        .fontSize(0.78.cssRem)
        .color(Color.rgba(255, 255, 255, 0.74f))
        .lineHeight(1.55)
}

val MoreAboutCircleStyle = CssStyle.base {
    Modifier
        .width(3.2.cssRem)
        .height(3.2.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.25f))
        .borderRadius(50.percent)
        .backgroundColor(Colors.Transparent)
        .color(Colors.White)
}

val ServiceBookButtonStyle = CssStyle.base {
    Modifier
        .backgroundColor(Color.rgb(0x6E62A8))
        .color(Colors.White)
        .borderRadius(0.7.cssRem)
        .padding(leftRight = 1.1.cssRem, topBottom = 0.55.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .opacity(0.0)
        .transform { translateY(6.px) }
        .transition {
            property("opacity")
            property("transform")
            duration(200.ms)
            timingFunction(AnimationTimingFunction.Ease)
        }
}

@Composable
private fun ServiceRow(name: String, price: String) {
    var isHovered by remember { mutableStateOf(false) }
    Row(
        ServiceRowStyle.toModifier()
            .onMouseEnter { isHovered = true }
            .onMouseLeave { isHovered = false }
    ) {
        P(ServiceNameStyle.toModifier().flex(1).margin(right = 3.2.cssRem).toAttrs {
            style { property("white-space", "nowrap") }
        }) { Text(name) }
        Row(Modifier.gap(0.9.cssRem).alignItems(AlignItems.Center)) {
            Box(
                Modifier.position(Position.Relative).minWidth(10.cssRem).display(DisplayStyle.Flex).justifyContent(JustifyContent.FlexEnd),
                contentAlignment = Alignment.CenterEnd
            ) {
            P(
                ServicePriceStyle.toModifier()
                    .opacity(if (isHovered) 0.0 else 1.0)
                    .transform { translateY(if (isHovered) (-4).px else 0.px) }
                    .toAttrs {
                        style { property("white-space", "nowrap") }
                    }
            ) {
                Text(price)
            }
            Button(
                onClick = { kotlinx.browser.window.location.href = "/book" },
                modifier = ServiceBookButtonStyle.toModifier()
                    .opacity(if (isHovered) 1.0 else 0.0)
                    .transform { translateY(if (isHovered) 0.px else 6.px) }
                    .pointerEvents(if (isHovered) PointerEvents.Auto else PointerEvents.None)
                    .position(Position.Absolute)
                    .right(0.px)
            ) {
                Text("Book Now  \u2192")
            }
            }
        }
    }
}

private data class Review(val name: String, val role: String, val text: String, val pfp: String)
private data class ReviewGridItem(
    val name: String,
    val handle: String,
    val text: String,
    val pfp: String,
    val photo: String? = null,
    val featured: Boolean = false
)
private data class FaqItem(val question: String, val answer: String)
private data class LocationSuggestion(val title: String, val subtitle: String, val icon: String)

@Composable
private fun ReviewStripCard(review: Review) {
    Div(ReviewStripCardStyle.toModifier().toAttrs()) {
        Row(Modifier.gap(0.45.cssRem).alignItems(AlignItems.Center)) {
            Img(
                src = review.pfp,
                alt = review.name,
                attrs = Modifier.size(1.6.cssRem).borderRadius(50.percent).border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.25f)).toAttrs()
            )
            Column(Modifier.gap(0.1.cssRem)) {
                P(ReviewStripNameStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.name) }
                P(ReviewStripMetaStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.role) }
            }
        }
        P(ReviewStripTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.text) }
    }
}

@Composable
private fun ReviewGridCard(review: ReviewGridItem) {
    val cardModifier = if (review.featured) {
        ReviewGridCardStyle.toModifier().then(ReviewGridFeaturedCardStyle.toModifier())
    } else {
        ReviewGridCardStyle.toModifier()
    }
    Div(
        cardModifier.toAttrs {
            style {
                property("break-inside", "avoid")
                property("page-break-inside", "avoid")
            }
        }
    ) {
        P(ReviewGridStarsStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("★★★★★") }
        Row(Modifier.gap(0.55.cssRem).alignItems(AlignItems.Center)) {
            Img(
                src = review.pfp,
                alt = review.name,
                attrs = Modifier.size(1.7.cssRem).borderRadius(50.percent).border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.25f)).toAttrs()
            )
            Column(Modifier.gap(0.1.cssRem)) {
                P(ReviewGridNameStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.name) }
                P(ReviewGridRoleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.handle) }
            }
        }
        P(ReviewGridTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.text) }
        if (review.photo != null) {
            Img(
                src = review.photo,
                alt = review.name,
                attrs = ReviewGridPhotoStyle.toModifier().toAttrs {
                    style { property("object-fit", "cover") }
                }
            )
        }
    }
}

@Composable
private fun FaqRow(item: FaqItem, isOpen: Boolean, onToggle: () -> Unit) {
    Div(FaqItemStyle.toModifier().toAttrs()) {
        Row(
            FaqQuestionRowStyle.toModifier().onClick { onToggle() }
        ) {
            P(FaqQuestionTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(item.question) }
            P(FaqIconStyle.toModifier().margin(bottom = 0.px).toAttrs {
                if (isOpen) {
                    style { property("transform", "rotate(45deg)") }
                }
            }) { Text("+") }
        }
        val answerModifier = if (isOpen) {
            FaqAnswerWrapStyle.toModifier().then(FaqAnswerOpenStyle.toModifier())
        } else {
            FaqAnswerWrapStyle.toModifier()
        }
        Div(answerModifier.toAttrs()) {
            P(FaqAnswerTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(item.answer) }
        }
    }
}

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home", wideContent = true))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    DisposableEffect(Unit) {
        val styleElement = document.createElement("style")
        styleElement.textContent = """
            @keyframes reviewMarquee {
                from { transform: translateX(0); }
                to { transform: translateX(-50%); }
            }
            @keyframes fleetEnter {
                from { opacity: 0; transform: translateX(10px); }
                to { opacity: 1; transform: translateX(0); }
            }
            @keyframes fleetExit {
                from { opacity: 1; transform: translateX(0); }
                to { opacity: 0; transform: translateX(-10px); }
            }
            input[placeholder="Pick-up location"]::placeholder,
            input[placeholder="Drop-off location"]::placeholder {
                color: rgba(255, 255, 255, 0.9);
                opacity: 1;
            }
        """.trimIndent()
        document.head?.appendChild(styleElement)
        onDispose { styleElement.remove() }
    }
    val pickupOptions = remember {
        listOf(
            LocationSuggestion("Allow location access", "It provides your pickup address", "/current%20location%20icon.svg"),
            LocationSuggestion("Beijing Tong Ren Tang", "124 Shaftesbury Ave, London, W1D 5ES, GB", "/location%20icon.svg"),
            LocationSuggestion("Curzon Soho", "99 Shaftesbury Ave, London, W1D 5DY, GB", "/location%20icon.svg"),
            LocationSuggestion("Genting Casino Chinatown", "93-107 Shaftesbury Ave, London, W1D 5EJ, GB", "/location%20icon.svg"),
            LocationSuggestion("Heytea Soho", "93-107 Shaftesbury Ave, London, W1D 5DA, GB", "/location%20icon.svg"),
            LocationSuggestion("Bab N Suul", "1 Gerrard Place, London, W1D 5PA, GB", "/location%20icon.svg"),
            LocationSuggestion("Xing Fu Tang", "29 Frith St, Soho, London, W1D 5LB, GB", "/location%20icon.svg"),
            LocationSuggestion("Gerrard Place / Chinatown PBUS Stop", "Shaftesbury Ave, London, GB", "/location%20icon.svg")
        )
    }
    val reviews = remember {
        listOf(
            Review("Medite Ranjia", "Business Man", "Best trim I’ve had in a long time. He actually listens to what you want and gets it right the first time.", "/pfp1.jfif"),
            Review("Klara Ivanov", "Product Designer", "Sharp cut and easy booking. The service feels premium from start to finish.", "/pfp2.jfif"),
            Review("Aiden Brooks", "Photographer", "Great attention to detail and perfect beard shaping. Clean and professional.", "/pfp3.jfif"),
            Review("Noah Keller", "Architect", "Top-tier experience and excellent styling. Walked out feeling sharp.", "/pfp4.jfif"),
            Review("Isla Romero", "Content Creator", "Perfect fade and clean lines. The atmosphere is calm and welcoming.", "/pfp5.jfif"),
            Review("Maya Chen", "Marketing Lead", "Quick, clean, and exactly what I wanted. Highly recommend this spot.", "/pfp6.jfif")
        )
    }
    val scope = rememberCoroutineScope()
    val marqueeReviews = remember(reviews) { List(6) { reviews }.flatten() }
    val reviewGridItems = remember {
        listOf(
            ReviewGridItem(
                "Adam C.",
                "@adamcmanthiel",
                "This has been helpful with easing my ADHD tendencies. SubStack cut how I use social apps.",
                "/pfp%2016.png"
            ),
            ReviewGridItem(
                "Matt Murray",
                "@mattsmurray",
                "Also noticed a change after a week. Less doom scrolling and more focus on real tasks.",
                "/pfp%2013.png"
            ),
            ReviewGridItem(
                "Ishmeet S.",
                "@ishmeet_s",
                "Working well so far. I think the physical barrier helps me stay mindful.",
                "/pfp%2014.png"
            ),
            ReviewGridItem(
                "Rishi Parikh",
                "@rishiparikh01",
                "This block saved me. My productivity has skyrocketed and I feel less distracted.",
                "/pfp%2015.png"
            ),
            ReviewGridItem(
                "Adam",
                "@adamcmanthiel",
                "Got my first block and I love it. It makes it harder to get lost in the scroll.",
                "/pfp%2010.jpeg",
                "/image%20188.png",
                featured = true
            ),
            ReviewGridItem(
                "Adi",
                "@adiora710",
                "Loving the block so far. It's helped me break the habit of constant checking.",
                "/pfp%2011.jpeg"
            ),
            ReviewGridItem(
                "Devan",
                "@devansoul",
                "I got the block and I love it. Visiting @bulkspace is my new routine.",
                "/pfp%2012.jpeg"
            ),
            ReviewGridItem(
                "Maya Chen",
                "@mayachen",
                "Booking was smooth and the driver was right on time. Quiet ride and spotless interior.",
                "/pfp%2013.png"
            ),
            ReviewGridItem(
                "Noah Keller",
                "@noahkeller",
                "Professional, courteous, and punctual. Felt premium from pickup to drop-off.",
                "/pfp%2014.png"
            ),
            ReviewGridItem(
                "Klara Ivanov",
                "@klarai",
                "Loved the attention to detail and comfort. Great for business travel.",
                "/pfp%2015.png"
            ),
            ReviewGridItem(
                "Isla Romero",
                "@islaromero",
                "Smooth airport transfer with plenty of luggage space. Would book again.",
                "/pfp%2016.png"
            ),
            ReviewGridItem(
                "Aiden Brooks",
                "@aidenb",
                "On-time arrival, clean vehicle, and a calm experience through the city.",
                "/pfp%2011.jpeg"
            ),
            ReviewGridItem(
                "Rishi Parikh",
                "@rishiparikh01",
                "Punctual service and a very comfortable ride. The details feel first-class.",
                "/pfp%2010.jpeg",
                "/image%20188.png"
            ),
            ReviewGridItem(
                "Matt Murray",
                "@mattsmurray",
                "Driver was professional and the ride was quiet. Perfect for early flights.",
                "/pfp%2013.png"
            ),
            ReviewGridItem(
                "Adam C.",
                "@adamcmanthiel",
                "Booked last minute and still got excellent service. Highly recommend.",
                "/pfp%2016.png"
            )
        )
    }
    val faqItems = remember {
        listOf(
            FaqItem("What areas do you cover?", "We serve London and surrounding areas, with long-distance journeys available on request."),
            FaqItem("How far in advance should I book?", "We recommend booking 24–48 hours in advance, but same-day availability is often possible."),
            FaqItem("Can I schedule multiple stops?", "Yes. Add as many stops as you need and your chauffeur will follow the planned route."),
            FaqItem("What vehicles are available?", "Choose from saloon, executive, estate, and MPV options depending on your group size and luggage."),
            FaqItem("Do you provide airport meet & greet?", "Yes. We can meet you inside the terminal with your name board and assist with luggage."),
            FaqItem("Is there a cancellation fee?", "Cancellations are free up to 24 hours before pickup. Later cancellations may incur a fee."),
            FaqItem("Do you track flights?", "Yes. We track your flight and adjust pickup time for delays or early arrivals."),
            FaqItem("Is the service available 24/7?", "Yes. Chauffeur bookings are available day and night, subject to availability."),
            FaqItem("Can I book a return journey?", "Yes. You can book a return transfer at the same time or schedule it separately."),
            FaqItem("Do you provide child seats?", "Child seats are available upon request. Let us know the age and type needed."),
            FaqItem("How do I change my booking?", "Contact our team with your reference and we will update your reservation."),
            FaqItem("Are your chauffeurs licensed?", "Yes. All drivers are fully licensed, insured, and professionally trained."),
            FaqItem("Can I request a specific vehicle?", "Yes. Select your preferred vehicle type during booking."),
            FaqItem("Do you offer corporate accounts?", "Yes. We provide tailored corporate travel solutions and invoicing."),
            FaqItem("Is luggage assistance included?", "Yes. Chauffeurs assist with luggage during pickup and drop-off.")
        )
    }
    var pickupQuery by remember { mutableStateOf("") }
    var dropoffQuery by remember { mutableStateOf("") }
    var pickupOpen by remember { mutableStateOf(false) }
    var dropoffOpen by remember { mutableStateOf(false) }
    var pickupTime by remember { mutableStateOf("Pick-up now") }
    var pickupTimeOpen by remember { mutableStateOf(false) }
    var openFaqIndex by remember { mutableStateOf<Int?>(null) }
    val pickupMatches = pickupOptions.filter {
        pickupQuery.isBlank() || it.title.contains(pickupQuery, ignoreCase = true) || it.subtitle.contains(pickupQuery, ignoreCase = true)
    }
    val dropoffMatches = pickupOptions.filter {
        dropoffQuery.isBlank() || it.title.contains(dropoffQuery, ignoreCase = true) || it.subtitle.contains(dropoffQuery, ignoreCase = true)
    }

    Div(HeroContainerStyle.toAttrs()) {
        Div(HeroGridStyle.toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(0.45.cssRem).alignItems(AlignItems.Center)) {
                Div(
                    EyebrowStyle.toModifier().toAttrs {
                        style {
                            property("backdrop-filter", "blur(12px)")
                            property("-webkit-backdrop-filter", "blur(12px)")
                        }
                    }
                ) {
                    Text("THE ELITE WAY TO TRAVEL")
                }
                H1(TitleStyle.toAttrs()) { Text("AEROVA ELITE") }
                P(HeroSubtextStyle.toAttrs()) { Text("Luxury Airport Transfers. Arrive First Class.") }
                Row(HeroButtonsRowStyle.toModifier()) {
                    Div(
                        HeroPillContainerStyle.toAttrs {
                            style {
                                property("backdrop-filter", "blur(18px)")
                                property("-webkit-backdrop-filter", "blur(18px)")
                            }
                        }
                    ) {
                        SpanText("Reliable. Private. Luxurious.", HeroPillTextStyle.toModifier())
                        Button(onClick = { kotlinx.browser.window.location.href = "/book" }, modifier = HeroPillButtonStyle.toModifier()) {
                            Text("Book Your Ride  →")
                        }
                    }
                }
                Div(
                    SearchPanelStyle.toModifier().toAttrs {
                        style {
                            property("backdrop-filter", "blur(16px)")
                            property("-webkit-backdrop-filter", "blur(16px)")
                        }
                    }
                ) {
                    Row(SearchTrackStyle.toModifier()) {
                        Img(src = "/start%20circle.svg", alt = "Start", attrs = Modifier.size(1.1.cssRem).toAttrs())
                        Div(SearchTrackLineStyle.toModifier().toAttrs())
                        Img(src = "/end%20square.svg", alt = "End", attrs = Modifier.size(1.1.cssRem).toAttrs())
                    }
                    Row(SearchRowStyle.toModifier()) {
                        Box(SearchFieldWrapStyle.toModifier()) {
                            Row(SearchFieldStyle.toModifier()) {
                                Img(src = "/search%20location%20icon.svg", alt = "Pickup", attrs = Modifier.size(1.1.cssRem).toAttrs())
                                Input(
                                    type = InputType.Text,
                                    attrs = SearchInputStyle.toModifier().toAttrs {
                                        placeholder("Pick-up location")
                                        value(pickupQuery)
                                        onInput { event ->
                                            pickupQuery = event.value
                                            pickupOpen = true
                                        }
                                        onFocus { pickupOpen = true }
                                        onBlur {
                                            scope.launch {
                                                delay(120)
                                                pickupOpen = false
                                            }
                                        }
                                        style {
                                            property("border", "none")
                                            property("outline", "none")
                                        }
                                    }
                                )
                                Img(src = "/near%20me%20icon.svg", alt = "Near me", attrs = Modifier.size(1.05.cssRem).toAttrs())
                            }
                            if (pickupOpen) {
                                Div(SearchDropdownStyle.toModifier().toAttrs {
                                    style {
                                        property("backdrop-filter", "blur(16px)")
                                        property("-webkit-backdrop-filter", "blur(16px)")
                                    }
                                }) {
                                    pickupMatches.forEach { option ->
                                        Row(
                                            SearchDropdownItemStyle.toModifier().onMouseDown {
                                                pickupQuery = option.title
                                                pickupOpen = false
                                            }
                                        ) {
                                            Img(src = option.icon, alt = option.title, attrs = Modifier.size(1.1.cssRem).toAttrs())
                                            Column(Modifier.gap(0.1.cssRem)) {
                                                P(SearchDropdownTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(option.title) }
                                                P(SearchDropdownSubtitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(option.subtitle) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Box(SearchFieldWrapStyle.toModifier()) {
                            Row(SearchFieldStyle.toModifier()) {
                                Img(src = "/search%20location%20icon.svg", alt = "Drop-off", attrs = Modifier.size(1.1.cssRem).toAttrs())
                                Input(
                                    type = InputType.Text,
                                    attrs = SearchInputStyle.toModifier().toAttrs {
                                        placeholder("Drop-off location")
                                        value(dropoffQuery)
                                        onInput { event ->
                                            dropoffQuery = event.value
                                            dropoffOpen = true
                                        }
                                        onFocus { dropoffOpen = true }
                                        onBlur {
                                            scope.launch {
                                                delay(120)
                                                dropoffOpen = false
                                            }
                                        }
                                        style {
                                            property("border", "none")
                                            property("outline", "none")
                                        }
                                    }
                                )
                            }
                            if (dropoffOpen) {
                                Div(SearchDropdownStyle.toModifier().toAttrs {
                                    style {
                                        property("backdrop-filter", "blur(16px)")
                                        property("-webkit-backdrop-filter", "blur(16px)")
                                    }
                                }) {
                                    dropoffMatches.forEach { option ->
                                        Row(
                                            SearchDropdownItemStyle.toModifier().onMouseDown {
                                                dropoffQuery = option.title
                                                dropoffOpen = false
                                            }
                                        ) {
                                            Img(src = option.icon, alt = option.title, attrs = Modifier.size(1.1.cssRem).toAttrs())
                                            Column(Modifier.gap(0.1.cssRem)) {
                                                P(SearchDropdownTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(option.title) }
                                                P(SearchDropdownSubtitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(option.subtitle) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Box(Modifier.position(Position.Relative).minWidth(10.cssRem)) {
                            Row(
                                SearchTimeFieldStyle.toModifier().onClick {
                                    pickupTimeOpen = !pickupTimeOpen
                                }
                            ) {
                                Img(src = "/clock%20icon.svg", alt = "Time", attrs = Modifier.size(1.05.cssRem).toAttrs())
                                SpanText(pickupTime, SearchTimeTextStyle.toModifier())
                                Img(
                                    src = "/down%20arrow.svg",
                                    alt = "Open",
                                    attrs = Modifier.size(0.85.cssRem).toAttrs {
                                        style {
                                            property("filter", "brightness(0)")
                                            property("pointer-events", "none")
                                        }
                                    }
                                )
                            }
                            if (pickupTimeOpen) {
                                Column(SearchTimeMenuStyle.toModifier()) {
                                    listOf("Pick-up now", "Schedule Later").forEach { option ->
                                        val isSelected = pickupTime == option
                                        Row(
                                            SearchTimeOptionStyle.toModifier()
                                                .backgroundColor(if (isSelected) Color.rgba(0, 0, 0, 0.08f) else Colors.Transparent)
                                                .onClick {
                                                    pickupTime = option
                                                    pickupTimeOpen = false
                                                }
                                        ) {
                                            Box(
                                                SearchTimeRadioStyle.toModifier()
                                                    .backgroundColor(if (isSelected) Color.rgb(0x0B0B0D) else Colors.Transparent)
                                            )
                                            Text(option)
                                        }
                                    }
                                }
                            }
                        }
                        Button(onClick = {}, modifier = SearchButtonStyle.toModifier()) {
                            Img(
                                src = "/search%20location%20icon.svg",
                                alt = "Search",
                                attrs = Modifier.size(1.2.cssRem).toAttrs {
                                    style { property("filter", "brightness(0)") }
                                }
                            )
                        }
                    }
                }
            }
        }
        Column(Modifier.gap(0.6.cssRem).margin(top = 2.2.cssRem).alignItems(AlignItems.Center)) {
            SpanText("MORE ABOUT US", Modifier.color(Color.rgba(255, 255, 255, 0.75f)).letterSpacing(0.3.cssRem))
            Button(onClick = { kotlinx.browser.window.location.hash = "about" }, modifier = MoreAboutCircleStyle.toModifier()) {
                Svg(attrs = Modifier.size(1.2.cssRem).toAttrs {
                    attr("viewBox", "0 0 24 24")
                    attr("fill", "none")
                    attr("stroke", "currentColor")
                    attr("stroke-width", "2")
                    attr("stroke-linecap", "round")
                    attr("stroke-linejoin", "round")
                }) {
                    Path(attrs = {
                        attr("d", "M6 9l6 6 6-6")
                    })
                }
            }
        }
        Column(Modifier.fillMaxWidth().gap(0.9.cssRem).margin(top = 2.6.cssRem)) {
            Div(ReviewMarqueeViewportStyle.toModifier().toAttrs()) {
                Div(ReviewMarqueeTrackStyle.toModifier().toAttrs {
                    style {
                        property("width", "max-content")
                        property("animation", "reviewMarquee 42s linear infinite")
                        property("will-change", "transform")
                    }
                }) {
                    marqueeReviews.forEach { review ->
                        ReviewStripCard(review)
                    }
                }
            }
        }
        Div(SectionDividerStyle.toModifier().toAttrs())
        Div(AboutSectionStyle.toModifier().id("about").toAttrs {
            style { property("aspect-ratio", "16 / 9") }
        }) {
            P(AboutHeadingStyle.toModifier().toAttrs()) {
                SpanText("Precision", AboutHeadingAccentStyle.toModifier())
                Text(" Travel,")
                Br()
                SpanText("Professional", AboutHeadingAccentStyle.toModifier())
                Text(" Service")
            }
            P(AboutSubtextStyle.toModifier().toAttrs()) {
                Text("Our mission is simple: to provide a travel experience that is smooth, punctual,")
                Br()
                Text("and tailored to the needs of modern travellers.")
            }
            Row(HeroButtonsRowStyle.toModifier()) {
                Div(
                    HeroPillContainerStyle.toAttrs {
                        style {
                            property("backdrop-filter", "blur(18px)")
                            property("-webkit-backdrop-filter", "blur(18px)")
                        }
                    }
                ) {
                    SpanText("Based in London, Serving the UK", HeroPillTextStyle.toModifier())
                    Button(onClick = { kotlinx.browser.window.location.href = "/book" }, modifier = HeroPillButtonStyle.toModifier()) {
                        Text("Book Your Ride  →")
                    }
                }
            }
            Div(AboutMapStyle.toModifier().toAttrs {
                style {
                    property("background-image", "url('/MAP.png')")
                    property("background-repeat", "no-repeat")
                    property("background-position", "center")
                    property("background-size", "contain")
                }
            })
        }
        Div(SectionDividerStyle.toModifier().toAttrs())
        data class FleetCar(
            val name: String,
            val image: String,
            val passengers: String,
            val suitcases: String,
            val handLuggage: String,
            val features: String,
            val passengersText: String,
            val suitcasesText: String,
            val handLuggageText: String,
            val featuresText: String
        )
        val cars = remember {
            listOf(
                FleetCar(
                    "SALOON",
                    "/saloon.jpg",
                    "4",
                    "4",
                    "3",
                    "3",
                    "Comfortably seats up to four passengers with ample legroom.",
                    "Accommodates up to four large checked suitcases.",
                    "Space for cabin bags, backpacks, or carry-on items.",
                    "Premium amenities including climate control and phone charging."
                ),
                FleetCar(
                    "ESTATE",
                    "/estates.png",
                    "4",
                    "5",
                    "3",
                    "3",
                    "Spacious seating for four with refined comfort.",
                    "Extended luggage capacity for larger trips.",
                    "Room for cabin bags and personal items.",
                    "Comfort features with premium interior finishes."
                ),
                FleetCar(
                    "EV",
                    "/EV.jpg",
                    "4",
                    "3",
                    "3",
                    "3",
                    "Quiet electric ride with comfortable seating for four.",
                    "Fits up to three large checked suitcases.",
                    "Space for cabin luggage and backpacks.",
                    "Modern amenities with smooth, silent travel."
                ),
                FleetCar(
                    "EXECUTIVE",
                    "/Executive.jpg",
                    "4",
                    "4",
                    "3",
                    "3",
                    "Comfortably seats up to four passengers with ample legroom for executive travel.",
                    "Accommodates up to four large checked suitcases in the rear luggage compartment.",
                    "Space for cabin bags, backpacks, or small carry-on items.",
                    "Includes premium amenities such as climate control, leather seating, and phone charging."
                ),
                FleetCar(
                    "MPV EXECUTIVE",
                    "/MPV%20Executive.jpg",
                    "6",
                    "6",
                    "4",
                    "3",
                    "Flexible seating for up to six passengers.",
                    "Generous luggage space for larger groups.",
                    "Ample room for carry-on items.",
                    "Enhanced comfort with premium amenities."
                )
            )
        }
        var currentCarIndex by remember { mutableStateOf(0) }
        var previousCarIndex by remember { mutableStateOf(0) }
        DisposableEffect(cars) {
            val job = scope.launch {
                while (true) {
                    delay(5200)
                    previousCarIndex = currentCarIndex
                    currentCarIndex = (currentCarIndex + 1) % cars.size
                }
            }
            onDispose { job.cancel() }
        }
        val currentCar = cars[currentCarIndex]
        val previousCar = cars[previousCarIndex]
        Div(FleetSectionStyle.toModifier().id("fleet").toAttrs {
            style { property("aspect-ratio", "16 / 9") }
        }) {
            Box(FleetHeaderWrapStyle.toModifier()) {
                Column(Modifier.fillMaxWidth().alignItems(AlignItems.Center)) {
                    P(FleetTitleStyle.toAttrs()) {
                        Text("Our ")
                        SpanText("Fleet", FleetTitleAccentStyle.toModifier())
                    }
                    P(FleetSubtextStyle.toAttrs()) { Text("Luxury vehicles. Impeccable comfort. Professional chauffeur service.") }
                }
                Box(FleetStatsPanelStyle.toModifier()) {
                    Column(Modifier.position(Position.Relative)) {
                        P(
                            Modifier.fontSize(1.25.cssRem)
                                .letterSpacing(0.28.cssRem)
                                .color(Colors.White)
                                .margin(left = 0.95.cssRem, bottom = 0.6.cssRem)
                                .toAttrs()
                        ) {
                            Text(currentCar.name)
                        }
                        Box(Modifier.position(Position.Relative)) {
                            Div(Modifier.fillMaxSize().position(Position.Absolute).toAttrs {
                                style { property("pointer-events", "none") }
                            }) {
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("height", "1px")
                                        property("left", "-6%")
                                        property("right", "-6%")
                                        property("top", "0")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("height", "1px")
                                        property("left", "-6%")
                                        property("right", "-6%")
                                        property("top", "50%")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("height", "1px")
                                        property("left", "-6%")
                                        property("right", "-6%")
                                        property("top", "100%")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("width", "1px")
                                        property("top", "-10%")
                                        property("bottom", "-10%")
                                        property("left", "0")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("width", "1px")
                                        property("top", "-10%")
                                        property("bottom", "-10%")
                                        property("left", "50%")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                                Div(Modifier.position(Position.Absolute).toAttrs {
                                    style {
                                        property("width", "1px")
                                        property("top", "-10%")
                                        property("bottom", "-10%")
                                        property("right", "0")
                                        property("background-color", "rgba(255,255,255,0.16)")
                                    }
                                })
                            }
                            Div(FleetStatGridStyle.toModifier().toAttrs {
                                style {
                                    property("grid-template-columns", "1fr 1fr")
                                    property("grid-template-rows", "1fr 1fr")
                                }
                            }) {
                                Div(FleetStatCellStyle.toAttrs()) {
                                    Img(src = "/passenger%20icon.svg", alt = "Passengers", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                    Column(Modifier.gap(0.25.cssRem)) {
                                        P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                            SpanText("PASSENGERS: ")
                                            SpanText(currentCar.passengers, FleetStatNumberStyle.toModifier())
                                        }
                                        P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(currentCar.passengersText) }
                                    }
                                }
                                Div(FleetStatCellStyle.toAttrs()) {
                                    Img(src = "/suitcase%20icon.svg", alt = "Suitcases", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                    Column(Modifier.gap(0.25.cssRem)) {
                                        P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                            SpanText("SUITCASES: ")
                                            SpanText(currentCar.suitcases, FleetStatNumberStyle.toModifier())
                                        }
                                        P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(currentCar.suitcasesText) }
                                    }
                                }
                                Div(FleetStatCellStyle.toAttrs()) {
                                    Img(src = "/hand%20luggage%20icon.svg", alt = "Hand Luggage", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                    Column(Modifier.gap(0.25.cssRem)) {
                                        P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                            SpanText("HAND LUGGAGE: ")
                                            SpanText(currentCar.handLuggage, FleetStatNumberStyle.toModifier())
                                        }
                                        P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(currentCar.handLuggageText) }
                                    }
                                }
                                Div(FleetStatCellStyle.toAttrs()) {
                                    Img(src = "/features%20icon.svg", alt = "Features", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                    Column(Modifier.gap(0.25.cssRem)) {
                                        P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                            SpanText("FEATURES: ")
                                            SpanText(currentCar.features, FleetStatNumberStyle.toModifier())
                                        }
                                        P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(currentCar.featuresText) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box(FleetImageWrapStyle.toModifier(), contentAlignment = Alignment.Center) {
                Row(
                    Modifier.fillMaxWidth()
                        .justifyContent(JustifyContent.SpaceBetween)
                        .alignItems(AlignItems.Center)
                        .transform { translateY(5.2.cssRem) }
                ) {
                    Button(onClick = {
                        previousCarIndex = currentCarIndex
                        currentCarIndex = (currentCarIndex - 1 + cars.size) % cars.size
                    }, modifier = FleetArrowButtonStyle.toModifier()) {
                        Img(src = "/LEFT%20CAR%20ARROW.svg", alt = "Prev", attrs = Modifier.size(1.9.cssRem).toAttrs())
                    }
                    Box(FleetCarFrameStyle.toModifier()) {
                        if (previousCarIndex != currentCarIndex) {
                            key("prev-$previousCarIndex") {
                                Img(
                                    src = previousCar.image,
                                    alt = previousCar.name,
                                    attrs = Modifier.fillMaxWidth().toAttrs {
                                        style {
                                            property("position", "absolute")
                                            property("left", "0")
                                            property("right", "0")
                                            property("object-fit", "contain")
                                            property("animation", "fleetExit 2200ms ease forwards")
                                        }
                                    }
                                )
                            }
                        }
                        key("cur-$currentCarIndex") {
                            Img(
                                src = currentCar.image,
                                alt = currentCar.name,
                                attrs = Modifier.fillMaxWidth().toAttrs {
                                    style {
                                        property("position", "absolute")
                                        property("left", "0")
                                        property("right", "0")
                                        property("object-fit", "contain")
                                        property("animation", "fleetEnter 2200ms ease forwards")
                                    }
                                }
                            )
                        }
                    }
                    Button(onClick = {
                        previousCarIndex = currentCarIndex
                        currentCarIndex = (currentCarIndex + 1) % cars.size
                    }, modifier = FleetArrowButtonStyle.toModifier()) {
                        Img(src = "/RIGHT%20CAR%20ARROW.svg", alt = "Next", attrs = Modifier.size(1.9.cssRem).toAttrs())
                    }
                }
            }
            Button(
                onClick = { kotlinx.browser.window.location.href = "/book" },
                modifier = HeroPillButtonStyle.toModifier().then(FleetCtaButtonStyle.toModifier())
            ) {
                Text("Book Your Ride  →")
            }
        }
        Div(SectionDividerStyle.toModifier().toAttrs())
        Div(ChauffeurSectionStyle.toModifier().id("chauffeur").toAttrs {
            style { property("aspect-ratio", "16 / 9") }
        }) {
            P(FleetTitleStyle.toModifier().toAttrs()) {
                SpanText("Travel With", FleetTitleAccentStyle.toModifier())
                Text(" Comfort")
                Br()
                SpanText("and ", FleetTitleAccentStyle.toModifier())
                Text("Professionalism")
            }
            P(FleetSubtextStyle.toModifier().toAttrs()) {
                Text("Our chauffeur service offers a refined travel experience with professional drivers, luxury")
                Br()
                Text("vehicles, and punctual service.")
            }
            Img(
                src = "/LUGGAGE.png",
                alt = "Luggage",
                attrs = ChauffeurLuggageImageStyle.toModifier().toAttrs {
                    style { property("object-fit", "contain") }
                }
            )
        }
        Div(SectionDividerStyle.toModifier().toAttrs())
        Div(VipTravelSectionStyle.toModifier().id("vip-travel").toAttrs {
            style {
                property("aspect-ratio", "16 / 9")
                property(
                    "background-image",
                    "linear-gradient(180deg, rgba(0,0,0,0.88) 0%, rgba(0,0,0,0.6) 45%, rgba(0,0,0,0.92) 100%), url('/VIP%20TRAVEL.png')"
                )
                property("background-size", "cover")
                property("background-position", "center")
            }
        }) {
            Column(Modifier.fillMaxWidth().alignItems(AlignItems.Center).gap(0.6.cssRem)) {
                P(FleetTitleStyle.toModifier().toAttrs()) {
                    Text("Travel in ")
                    SpanText("VIP Comfort", FleetTitleAccentStyle.toModifier())
                }
                P(FleetSubtextStyle.toModifier().toAttrs()) {
                    Text("Reserve your luxury chauffeur ride with ease. Professional drivers, premium vehicles,")
                    Br()
                    Text("and first-class comfort for every journey.")
                }
            }
            Row(Modifier.fillMaxWidth().justifyContent(JustifyContent.Center)) {
                Div(
                    HeroPillContainerStyle.toAttrs {
                        style {
                            property("backdrop-filter", "blur(18px)")
                            property("-webkit-backdrop-filter", "blur(18px)")
                        }
                    }
                ) {
                    SpanText("Luxury Chauffeur Service", HeroPillTextStyle.toModifier())
                    Button(onClick = { kotlinx.browser.window.location.href = "/book" }, modifier = HeroPillButtonStyle.toModifier()) {
                        Text("Book Your VIP Ride  →")
                    }
                }
            }
            Box(VipStatsPanelStyle.toModifier()) {
                Column(Modifier.position(Position.Relative)) {
                    P(VipStatsTitleStyle.toModifier().toAttrs()) { Text("VIP TRAVEL") }
                    Box(Modifier.position(Position.Relative)) {
                        Div(Modifier.fillMaxSize().position(Position.Absolute).toAttrs {
                            style { property("pointer-events", "none") }
                        }) {
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("height", "1px")
                                    property("left", "-6%")
                                    property("right", "-6%")
                                    property("top", "0")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("height", "1px")
                                    property("left", "-6%")
                                    property("right", "-6%")
                                    property("top", "50%")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("height", "1px")
                                    property("left", "-6%")
                                    property("right", "-6%")
                                    property("top", "100%")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("width", "1px")
                                    property("top", "-10%")
                                    property("bottom", "-10%")
                                    property("left", "0")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("width", "1px")
                                    property("top", "-10%")
                                    property("bottom", "-10%")
                                    property("left", "50%")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                            Div(Modifier.position(Position.Absolute).toAttrs {
                                style {
                                    property("width", "1px")
                                    property("top", "-10%")
                                    property("bottom", "-10%")
                                    property("right", "0")
                                    property("background-color", "rgba(255,255,255,0.16)")
                                }
                            })
                        }
                        Div(FleetStatGridStyle.toModifier().toAttrs {
                            style {
                                property("grid-template-columns", "1fr 1fr")
                                property("grid-template-rows", "1fr 1fr")
                            }
                        }) {
                            Div(FleetStatCellStyle.toAttrs()) {
                                Img(src = "/passenger%20icon.svg", alt = "Passengers", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                Column(Modifier.gap(0.25.cssRem)) {
                                    P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        SpanText("PASSENGERS: ")
                                        SpanText("4", FleetStatNumberStyle.toModifier())
                                    }
                                    P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        Text("Comfortable seating for up to four passengers.")
                                    }
                                }
                            }
                            Div(FleetStatCellStyle.toAttrs()) {
                                Img(src = "/suitcase%20icon.svg", alt = "Suitcases", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                Column(Modifier.gap(0.25.cssRem)) {
                                    P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        SpanText("SUITCASES: ")
                                        SpanText("4", FleetStatNumberStyle.toModifier())
                                    }
                                    P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        Text("Space for up to four large suitcases.")
                                    }
                                }
                            }
                            Div(FleetStatCellStyle.toAttrs()) {
                                Img(src = "/hand%20luggage%20icon.svg", alt = "Hand Luggage", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                Column(Modifier.gap(0.25.cssRem)) {
                                    P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        SpanText("HAND LUGGAGE: ")
                                        SpanText("3", FleetStatNumberStyle.toModifier())
                                    }
                                    P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        Text("Room for carry-on items and personal bags.")
                                    }
                                }
                            }
                            Div(FleetStatCellStyle.toAttrs()) {
                                Img(src = "/features%20icon.svg", alt = "Features", attrs = Modifier.size(1.3.cssRem).toAttrs())
                                Column(Modifier.gap(0.25.cssRem)) {
                                    P(FleetStatTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        SpanText("FEATURES: ")
                                        SpanText("3", FleetStatNumberStyle.toModifier())
                                    }
                                    P(FleetStatBodyStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                        Text("Premium amenities with climate control and charging.")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Div(SectionDividerStyle.toModifier().toAttrs())
        Div(ReviewGridSectionStyle.toModifier().id("reviews").toAttrs()) {
            P(FleetTitleStyle.toModifier().toAttrs()) {
                Text("What people are ")
                SpanText("saying", FleetTitleAccentStyle.toModifier())
            }
            P(FleetSubtextStyle.toModifier().toAttrs()) {
                Text("Real feedback from people who have experienced the difference.")
            }
            Div(ReviewGridWrapStyle.toModifier().toAttrs {
                style {
                    property("column-width", "18rem")
                    property("column-gap", "1rem")
                    property("column-fill", "balance")
                }
            }) {
                reviewGridItems.forEach { review ->
                    ReviewGridCard(review)
                }
            }
        }
        Div(TinyReviewSectionStyle.toModifier().toAttrs()) {
            Div(TinyReviewLineStyle.toModifier().toAttrs())
            Div(TinyReviewCardStyle.toModifier().toAttrs()) {
                P(TinyReviewTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                    Text("“I used block all day today. I left it at home. It felt quite literally like a breath of fresh air knowing I could not and would not respond to anything on socials.”")
                }
                Row(Modifier.gap(0.5.cssRem).alignItems(AlignItems.Center)) {
                    Img(
                        src = "/pfp%2014.png",
                        alt = "Harpriya B.",
                        attrs = Modifier.size(1.9.cssRem).borderRadius(50.percent).border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.25f)).toAttrs()
                    )
                    Column(Modifier.gap(0.1.cssRem)) {
                        P(TinyReviewNameStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("Harpriya B.") }
                        P(TinyReviewHandleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("@harpriya") }
                    }
                    P(TinyReviewStarsStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("★★★★★") }
                }
            }
            Div(TinyReviewLineStyle.toModifier().toAttrs())
        }
        Div(FaqSectionStyle.toModifier().id("faqs").toAttrs {
            style { property("aspect-ratio", "16 / 9") }
        }) {
            P(FleetTitleStyle.toModifier().toAttrs()) {
                Text("We often get ")
                SpanText("asked", FleetTitleAccentStyle.toModifier())
                Text("…")
            }
            P(FleetSubtextStyle.toModifier().toAttrs()) {
                Text("Quick answers to the questions people ask us most.")
            }
            Div(FaqWrapStyle.toModifier().toAttrs()) {
                Div(FaqListStyle.toModifier().toAttrs()) {
                    faqItems.forEachIndexed { index, item ->
                        FaqRow(item, openFaqIndex == index) {
                            openFaqIndex = if (openFaqIndex == index) null else index
                        }
                    }
                }
            }
        }
        Div(SeoSectionStyle.toModifier().toAttrs()) {
            P(FleetTitleStyle.toModifier().toAttrs()) { Text("Aerova Elite") }
            P(FleetSubtextStyle.toModifier().toAttrs()) {
                Text("Aerova Elite is a luxury chauffeur service providing premium private transportation across London and the United Kingdom.")
            }
            Div(SeoContentStyle.toModifier().toAttrs()) {
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Our service is built for travelers who expect more than just transportation. Whether you are heading to the airport, attending a business meeting, or traveling for leisure, Aerova Elite delivers a smooth, punctual, and first-class experience from pickup to destination.")
                }
                P(SeoHeadingStyle.toModifier().toAttrs()) { Text("Luxury Chauffeur Service in London") }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Aerova Elite offers professional chauffeur services across London and surrounding areas. Our experienced drivers ensure every journey is comfortable, safe, and efficient. Each chauffeur is professionally trained, courteous, and focused on delivering a refined travel experience.")
                }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Our fleet includes premium vehicles designed for executive travel, business trips, airport transfers, and private journeys. Every vehicle is maintained to the highest standards and equipped with modern comfort features to make every ride enjoyable.")
                }
                P(SeoHeadingStyle.toModifier().toAttrs()) { Text("Airport Transfers") }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Aerova Elite provides luxury airport transfer services to and from major UK airports including Heathrow Airport, Gatwick Airport, Stansted Airport, Luton Airport, and London City Airport.")
                }
                Ul(SeoListStyle.toModifier().toAttrs {
                    style { property("list-style-type", "disc") }
                }) {
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("On-time pickup and drop-off") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Flight tracking for delays or early arrivals") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Comfortable luxury vehicles") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Professional chauffeurs") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Stress-free airport travel") }
                }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Whether arriving in London or departing for an international flight, Aerova Elite guarantees a smooth and reliable airport transfer experience.")
                }
                P(SeoHeadingStyle.toModifier().toAttrs()) { Text("Executive and Private Travel") }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Our chauffeur services are ideal for business professionals, executives, tourists, and private clients who value comfort, discretion, and reliability. Aerova Elite provides transportation for:")
                }
                Ul(SeoListStyle.toModifier().toAttrs {
                    style { property("list-style-type", "disc") }
                }) {
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Corporate travel and business meetings") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Airport transfers and hotel pickups") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Private city travel") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Events and special occasions") }
                    Li(SeoListItemStyle.toModifier().toAttrs()) { Text("Long-distance travel across the UK") }
                }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Every journey is tailored to meet the needs of our clients, ensuring a premium travel experience every time.")
                }
                P(SeoHeadingStyle.toModifier().toAttrs()) { Text("Our Mission") }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Our mission is simple: to deliver precision travel with professionalism and luxury. Aerova Elite combines experienced chauffeurs, premium vehicles, and reliable service to create a seamless transportation experience for every client.")
                }
                P(SeoHeadingStyle.toModifier().toAttrs()) { Text("Based in London") }
                P(SeoParagraphStyle.toModifier().toAttrs()) {
                    Text("Aerova Elite is based in London and proudly serves clients across the United Kingdom.")
                }
            }
        }
    }
}
