package org.example.app.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.value
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Iframe
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.example.app.components.layouts.PageLayoutData
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLInputElement

private enum class BookingStep {
    Entry,
    Location,
    Professional,
    Service,
    Time,
    Confirm,
    Done
}

private data class BookingLocation(val name: String, val address: String, val city: String)
private data class BookingProfessional(val name: String, val avatar: String, val availability: String)
private data class BookingService(val name: String, val duration: String, val price: Int, val category: BookingCategory)
private data class BookingAddon(val name: String, val duration: String, val price: Int)
private data class BookingCalendarDay(val number: String, val label: String, val isMore: Boolean = false)

private enum class BookingCategory(val label: String) {
    All("All categories"),
    Haircuts("Haircuts"),
    BeardCuts("Beard cuts"),
    FaceCare("Face care"),
    HairCare("Hair care")
}

private fun encodeForMap(value: String): String = js("encodeURIComponent(value)") as String

val BookingPageStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .minHeight(84.vh)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .color(Color.rgb(0x111111))
        .borderRadius(1.6.cssRem)
        .padding(clamp(2.cssRem, 4.5.vw, 3.2.cssRem))
}

val BookingHeaderStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .gap(1.4.cssRem)
}

val BookingBrandPillStyle = CssStyle.base {
    Modifier
        .backgroundColor(Color.rgb(0x111111))
        .color(Color.rgb(0xFFFFFF))
        .borderRadius(1.4.cssRem)
        .padding(leftRight = 1.1.cssRem, topBottom = 0.45.cssRem)
        .letterSpacing(0.16.cssRem)
        .fontSize(0.7.cssRem)
        .fontWeight(FontWeight.Medium)
}

val BookingStepsRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.8.cssRem)
        .flexWrap(FlexWrap.Wrap)
        .color(Color.rgb(0x8C8C8C))
        .fontSize(0.7.cssRem)
        .letterSpacing(0.08.cssRem)
}

val BookingStepActiveStyle = CssStyle.base {
    Modifier.color(Color.rgb(0x111111)).fontWeight(FontWeight.Medium)
}

val BookingGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(1.5.cssRem, 3.5.vw, 2.4.cssRem))
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.3.fr); size(0.9.fr) }
    }
}

val BookingLocationGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(1.6.cssRem, 4.vw, 2.6.cssRem))
            .gridTemplateColumns { size(1.fr) }
            .alignItems(AlignItems.Stretch)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(0.46.fr); size(0.54.fr) }
    }
}

val BookingTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.6.cssRem, 3.2.vw, 2.2.cssRem))
        .fontWeight(FontWeight.SemiBold)
}

val BookingSubtitleStyle = CssStyle.base {
    Modifier
        .color(Color.rgb(0x666666))
        .fontSize(0.8.cssRem)
        .lineHeight(1.5)
}

val BookingPrimaryButtonStyle = CssStyle.base {
    Modifier
        .backgroundColor(Color.rgb(0x111111))
        .color(Color.rgb(0xFFFFFF))
        .borderRadius(0.7.cssRem)
        .padding(leftRight = 1.8.cssRem, topBottom = 0.7.cssRem)
        .fontWeight(FontWeight.Medium)
        .letterSpacing(0.08.cssRem)
}

val BookingCardStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .borderRadius(0.9.cssRem)
        .padding(1.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .transition {
            property("box-shadow")
            duration(180.ms)
        }
}

val BookingCardActiveStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0x111111))
        .boxShadow(offsetX = 0.px, offsetY = 8.px, blurRadius = 22.px, color = Color.rgba(0, 0, 0, 0.08f))
}

val BookingCardTitleStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.Medium).fontSize(0.9.cssRem)
}

val BookingCardMetaStyle = CssStyle.base {
    Modifier.color(Color.rgb(0x7A7A7A)).fontSize(0.7.cssRem)
}

val BookingOrderPanelStyle = CssStyle.base {
    Modifier
        .borderRadius(1.2.cssRem)
        .backgroundColor(Color.rgb(0xFCFCFC))
        .border(1.px, LineStyle.Dashed, Color.rgb(0xE3E3E3))
        .padding(1.6.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(1.cssRem)
        .boxShadow(offsetX = 0.px, offsetY = 10.px, blurRadius = 26.px, color = Color.rgba(0, 0, 0, 0.08f))
}

val BookingOrderPanelWrapStyle = CssStyle.base {
    Modifier.position(Position.Sticky).top(1.4.cssRem)
}

val BookingOrderTitleStyle = CssStyle.base {
    Modifier.fontSize(1.cssRem).fontWeight(FontWeight.SemiBold).letterSpacing(0.12.cssRem)
}

val BookingOrderRowStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).justifyContent(JustifyContent.SpaceBetween).fontSize(0.75.cssRem)
}

val BookingDividerStyle = CssStyle.base {
    Modifier.borderBottom(1.px, LineStyle.Dashed, Color.rgb(0xE2E2E2))
}

val BookingServiceGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr); size(1.fr) }
    }
}

val BookingAddonGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr); size(1.fr); size(1.fr) }
    }
}

val BookingProfessionalGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .gridTemplateColumns { size(1.fr); size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr); size(1.fr); size(1.fr) }
    }
}

val BookingProfessionalImageStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(6.5.cssRem)
        .borderRadius(0.8.cssRem)
}

val BookingMapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(clamp(22.cssRem, 38.vw, 28.cssRem))
        .borderRadius(1.2.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE2E2E2))
        .overflow(Overflow.Hidden)
}

val BookingMapFrameStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .borderRadius(1.2.cssRem)
        .transform { perspective(1200.px); rotateX(4.deg) }
}

val BookingMapFadeStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(0.px)
        .bottom(0.px)
        .left(0.px)
        .width(38.percent)
        .pointerEvents(PointerEvents.None)
}

val BookingMapControlsRowStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).gap(0.6.cssRem).alignItems(AlignItems.Center)
}

val BookingMapButtonStyle = CssStyle.base {
    Modifier
        .borderRadius(2.cssRem)
        .padding(leftRight = 0.9.cssRem, topBottom = 0.4.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .backgroundColor(Color.rgb(0xFFFFFF))
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
}

val BookingMapSearchStyle = CssStyle.base {
    Modifier
        .borderRadius(2.cssRem)
        .padding(leftRight = 0.9.cssRem, topBottom = 0.45.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .backgroundColor(Color.rgb(0xFFFFFF))
        .fontSize(0.75.cssRem)
        .width(12.cssRem)
}

val BookingDateRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.7.cssRem)
        .flexWrap(FlexWrap.Wrap)
}

val BookingDateChipStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .borderRadius(0.7.cssRem)
        .padding(leftRight = 0.75.cssRem, topBottom = 0.5.cssRem)
        .fontSize(0.68.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingDateChipActiveStyle = CssStyle.base {
    Modifier.backgroundColor(Color.rgb(0x111111)).color(Color.rgb(0xFFFFFF)).border(1.px, LineStyle.Solid, Color.rgb(0x111111))
}

val BookingTimeRowStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).gap(0.8.cssRem).flexWrap(FlexWrap.Wrap)
}

val BookingTimeChipStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .borderRadius(0.7.cssRem)
        .padding(leftRight = 1.cssRem, topBottom = 0.6.cssRem)
        .fontSize(0.73.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingTimeChipActiveStyle = CssStyle.base {
    Modifier.backgroundColor(Color.rgb(0x111111)).color(Color.rgb(0xFFFFFF)).border(1.px, LineStyle.Solid, Color.rgb(0x111111))
}

val BookingEntryGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(2.cssRem, 5.vw, 3.5.cssRem))
            .gridTemplateColumns { size(1.fr) }
            .alignItems(AlignItems.Center)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.1.fr) }
    }
}

val BookingEntryImageStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(clamp(18.cssRem, 32.vw, 26.cssRem))
        .borderRadius(1.4.cssRem)
}

val BookingDropdownStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .borderRadius(0.6.cssRem)
        .padding(leftRight = 1.cssRem, topBottom = 0.5.cssRem)
        .fontSize(0.75.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingAddonsWrapStyle = CssStyle.base {
    Modifier
        .overflow(Overflow.Hidden)
        .maxHeight(0.cssRem)
        .opacity(0.0)
        .transition {
            property("max-height")
            property("opacity")
            duration(240.ms)
            timingFunction(AnimationTimingFunction.Ease)
        }
}

val BookingAddonsOpenStyle = CssStyle.base {
    Modifier.maxHeight(60.cssRem).opacity(1.0)
}

val BookingCalendarShellStyle = CssStyle.base {
    Modifier
        .borderRadius(1.2.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE3E3E3))
        .padding(1.1.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingCalendarHeaderStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).justifyContent(JustifyContent.SpaceBetween)
}

val BookingCalendarMonthStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.Medium).fontSize(0.85.cssRem)
}

val BookingCalendarNavStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.45.cssRem)
}

val BookingCalendarNavButtonStyle = CssStyle.base {
    Modifier
        .size(2.1.cssRem)
        .borderRadius(50.percent)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .backgroundColor(Color.rgb(0xFFFFFF))
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .fontSize(0.85.cssRem)
}

val BookingCalendarTodayStyle = CssStyle.base {
    Modifier
        .borderRadius(2.cssRem)
        .padding(leftRight = 0.8.cssRem, topBottom = 0.35.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .fontSize(0.7.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingCalendarDayStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .gap(0.15.cssRem)
        .borderRadius(1.3.cssRem)
        .padding(leftRight = 0.65.cssRem, topBottom = 0.55.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE6E6E6))
        .fontSize(0.7.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .minWidth(3.2.cssRem)
}

val BookingCalendarDayActiveStyle = CssStyle.base {
    Modifier.backgroundColor(Color.rgb(0x111111)).color(Color.rgb(0xFFFFFF)).border(1.px, LineStyle.Solid, Color.rgb(0x111111))
}

val BookingCalendarDayNumberStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.Medium).fontSize(0.72.cssRem)
}

val BookingPaymentCardStyle = CssStyle.base {
    Modifier
        .borderRadius(0.7.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE1E1E1))
        .padding(leftRight = 0.9.cssRem, topBottom = 0.65.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .fontSize(0.75.cssRem)
}

val BookingPaymentOptionStyle = CssStyle.base {
    Modifier
        .borderRadius(0.8.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xE1E1E1))
        .padding(leftRight = 0.9.cssRem, topBottom = 0.65.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.SpaceBetween)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .fontSize(0.74.cssRem)
}

val BookingPaymentOptionActiveStyle = CssStyle.base {
    Modifier.border(1.px, LineStyle.Solid, Color.rgb(0x111111)).backgroundColor(Color.rgb(0x111111)).color(Color.rgb(0xFFFFFF))
}

val BookingConfirmCardStyle = CssStyle.base {
    Modifier
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .borderRadius(1.cssRem)
        .padding(1.2.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
}

val BookingDoneStyle = CssStyle.base {
    Modifier
        .padding(2.cssRem)
        .borderRadius(1.2.cssRem)
        .backgroundColor(Color.rgb(0xFFFFFF))
        .border(1.px, LineStyle.Solid, Color.rgb(0xE0E0E0))
        .textAlign(TextAlign.Center)
}

@InitRoute
fun initBookPage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Book Now", showFooter = false, wideContent = true))
}

@Page("/book")
@Layout(".components.layouts.PageLayout")
@Composable
fun BookPage() {
    val locations = listOf(
        BookingLocation("12 Pell", "12 Pell Street", "New York, NY 10013"),
        BookingLocation("Mulberry", "75 Mulberry Street", "New York, NY 10013")
    )
    val professionals = listOf(
        BookingProfessional("Ro", "/prof1.jfif", "Available Wednesday, Mar 4"),
        BookingProfessional("Jon", "/prof2.jfif", "Available Tomorrow"),
        BookingProfessional("Marco H.", "/prof3.jpg", "Available Tomorrow"),
        BookingProfessional("Kevin L.", "/prof4.jpg", "Available Tomorrow"),
        BookingProfessional("Sara P.", "/prof5.jpg", "Available Friday"),
        BookingProfessional("Dani K.", "/prof6.jpg", "Available Saturday"),
        BookingProfessional("Mia V.", "/prof7.jpg", "Available Sunday"),
        BookingProfessional("Leo R.", "/prof8.jpg", "Available Monday"),
        BookingProfessional("Noah C.", "/prof9.jpg", "Available Tuesday"),
        BookingProfessional("Ethan S.", "/prof10.jpg", "Available Wednesday")
    )
    val services = listOf(
        BookingService("Beard Trim", "20 mins", 40, BookingCategory.BeardCuts),
        BookingService("Haircut", "30 mins", 65, BookingCategory.Haircuts),
        BookingService("Scissor Cut", "45 mins", 80, BookingCategory.HairCare),
        BookingService("Haircut + Beard", "45 mins", 90, BookingCategory.Haircuts),
        BookingService("Kids Cut", "25 mins", 45, BookingCategory.HairCare),
        BookingService("Hot Towel Shave", "30 mins", 55, BookingCategory.FaceCare)
    )
    val addons = listOf(
        BookingAddon("Eyebrows", "10 mins", 10),
        BookingAddon("Beard Trim", "20 mins", 40),
        BookingAddon("Scissor Cut", "45 mins", 80),
        BookingAddon("Hot Towel", "15 mins", 25),
        BookingAddon("Hair Wash", "10 mins", 15),
        BookingAddon("Line Up", "10 mins", 20)
    )
    val dateOptions = listOf(
        BookingCalendarDay("22", "Sun"),
        BookingCalendarDay("23", "Mon"),
        BookingCalendarDay("24", "Tue"),
        BookingCalendarDay("25", "Wed"),
        BookingCalendarDay("26", "Thu"),
        BookingCalendarDay("27", "Fri"),
        BookingCalendarDay("28", "Sat"),
        BookingCalendarDay("1", "Sun"),
        BookingCalendarDay("2", "Mon"),
        BookingCalendarDay("3", "Tue"),
        BookingCalendarDay("+", "More", isMore = true)
    )
    val timeOptions = listOf("10:00am", "11:15am", "1:45pm", "3:00pm", "4:30pm", "6:00pm")

    var step by remember { mutableStateOf(BookingStep.Entry) }
    var selectedLocation by remember { mutableStateOf<BookingLocation?>(null) }
    var selectedProfessional by remember { mutableStateOf<BookingProfessional?>(null) }
    var selectedService by remember { mutableStateOf<BookingService?>(null) }
    val selectedAddons = remember { mutableStateListOf<BookingAddon>() }
    var selectedDate by remember { mutableStateOf(dateOptions.first()) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf(BookingCategory.All) }
    var mapSearchText by remember { mutableStateOf("12 Pell, New York") }
    var mapQuery by remember { mutableStateOf("12 Pell, New York") }

    val subtotal = (selectedService?.price ?: 0) + selectedAddons.sumOf { it.price }
    val tipAmount = if (step == BookingStep.Confirm) (subtotal * 0.2).toInt() else 0
    val total = subtotal + tipAmount
    val currency = "£"
    val filteredServices = if (selectedCategory == BookingCategory.All) {
        services
    } else {
        services.filter { it.category == selectedCategory }
    }
    val mapSrc = "https://maps.google.com/maps?q=${encodeForMap(mapQuery)}&t=k&z=16&output=embed"

    Div(BookingPageStyle.toModifier().toAttrs()) {
        Column(Modifier.fillMaxWidth().gap(2.cssRem)) {
            Row(BookingHeaderStyle.toModifier()) {
                Div(BookingBrandPillStyle.toAttrs()) { Text("PEAKY BLADES") }
                Row(BookingStepsRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    val steps = listOf("Location", "Professional", "Service", "Time", "Confirm", "Done")
                    steps.forEachIndexed { index, label ->
                        val isActive = when (step) {
                            BookingStep.Entry -> index == 0
                            BookingStep.Location -> index == 0
                            BookingStep.Professional -> index == 1
                            BookingStep.Service -> index == 2
                            BookingStep.Time -> index == 3
                            BookingStep.Confirm -> index == 4
                            BookingStep.Done -> index == 5
                        }
                        val modifier = if (isActive) BookingStepActiveStyle.toModifier() else Modifier
                        Span(modifier.toAttrs()) { Text(label) }
                    }
                }
            }

            when (step) {
                BookingStep.Entry -> {
                    Div(BookingEntryGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Your personal barber service any time") }
                            P(BookingSubtitleStyle.toAttrs()) {
                                Text("Book a visit with our team in seconds. Choose your location, professional, and time that works best for you.")
                            }
                            P(BookingSubtitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("2 locations") }
                            Button(onClick = { step = BookingStep.Location }, modifier = BookingPrimaryButtonStyle.toModifier()) {
                                Text("BOOK NOW")
                            }
                        }
                        Img(
                            src = "/barbershop%20pic.jpg",
                            alt = "Barbershop",
                            attrs = BookingEntryImageStyle.toModifier().toAttrs {
                                style { property("object-fit", "cover") }
                            }
                        )
                    }
                }
                BookingStep.Location -> {
                    Div(BookingLocationGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Choose a location") }
                            Row(BookingMapControlsRowStyle.toModifier()) {
                                Button(
                                    onClick = {
                                        val base = selectedLocation?.let { "${it.name}, ${it.city}" } ?: "Chinatown NYC"
                                        mapQuery = "Barbershop near $base"
                                        mapSearchText = base
                                    },
                                    modifier = BookingMapButtonStyle.toModifier()
                                ) {
                                    Text("📍 Nearby")
                                }
                                Button(
                                    onClick = {
                                        val value = mapSearchText.ifBlank { "12 Pell, New York" }
                                        mapQuery = value
                                    },
                                    modifier = BookingMapButtonStyle.toModifier()
                                ) {
                                    Text("🔍 Search")
                                }
                                Input(
                                    type = InputType.Text,
                                    attrs = BookingMapSearchStyle.toModifier().toAttrs {
                                        placeholder("Search")
                                        value(mapSearchText)
                                        onInput { event ->
                                            mapSearchText = (event.target as HTMLInputElement).value
                                        }
                                    }
                                )
                            }
                            Column(Modifier.gap(0.9.cssRem)) {
                                locations.forEach { location ->
                                    val isActive = selectedLocation?.name == location.name
                                    val modifier = if (isActive) BookingCardStyle.toModifier().then(BookingCardActiveStyle.toModifier()) else BookingCardStyle.toModifier()
                                    Div(
                                        modifier
                                            .cursor(Cursor.Pointer)
                                            .onClick {
                                                selectedLocation = location
                                                mapQuery = "${location.name}, ${location.city}"
                                                mapSearchText = location.name
                                                step = BookingStep.Professional
                                            }
                                            .toAttrs()
                                    ) {
                                        Column(Modifier.gap(0.35.cssRem)) {
                                            P(BookingCardTitleStyle.toAttrs()) { Text(location.name) }
                                            P(BookingCardMetaStyle.toAttrs()) { Text(location.address) }
                                            P(BookingCardMetaStyle.toAttrs()) { Text(location.city) }
                                        }
                                    }
                                }
                            }
                        }
                        Div(BookingMapStyle.toModifier().toAttrs()) {
                            Iframe(
                                attrs = BookingMapFrameStyle.toModifier().toAttrs {
                                    attr("src", mapSrc)
                                    attr("loading", "lazy")
                                    attr("allowfullscreen", "true")
                                    attr("referrerpolicy", "no-referrer-when-downgrade")
                                }
                            )
                            Div(
                                BookingMapFadeStyle.toModifier().toAttrs {
                                    style {
                                        property(
                                            "background",
                                            "linear-gradient(90deg, rgba(255,255,255,1) 0%, rgba(255,255,255,0.85) 55%, rgba(255,255,255,0) 100%)"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
                BookingStep.Professional -> {
                    Div(BookingGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Choose a professional") }
                            Div(BookingProfessionalGridStyle.toAttrs()) {
                                Div(
                                    BookingCardStyle.toModifier()
                                        .cursor(Cursor.Pointer)
                                        .onClick {
                                            selectedProfessional = BookingProfessional("Any professional", "/prof1.jfif", "Next available")
                                            step = BookingStep.Service
                                        }
                                        .toAttrs()
                                ) {
                                    Column(Modifier.gap(0.4.cssRem)) {
                                        P(BookingCardTitleStyle.toAttrs()) { Text("Choose a service first") }
                                        P(BookingCardMetaStyle.toAttrs()) { Text("Book with any professional") }
                                    }
                                }
                                professionals.forEach { professional ->
                                    Div(
                                        BookingCardStyle.toModifier()
                                            .cursor(Cursor.Pointer)
                                            .onClick {
                                                selectedProfessional = professional
                                                step = BookingStep.Service
                                            }
                                            .toAttrs()
                                    ) {
                                        Column(Modifier.gap(0.6.cssRem)) {
                                            Img(
                                                src = professional.avatar,
                                                alt = professional.name,
                                                attrs = BookingProfessionalImageStyle.toModifier().toAttrs {
                                                    style { property("object-fit", "cover") }
                                                }
                                            )
                                            P(BookingCardTitleStyle.toAttrs()) { Text(professional.name) }
                                            P(BookingCardMetaStyle.toAttrs()) { Text(professional.availability) }
                                        }
                                    }
                                }
                            }
                        }
                        Div(BookingOrderPanelWrapStyle.toAttrs()) {
                            BookingOrderPanel(
                                step = step,
                                location = selectedLocation,
                                professional = selectedProfessional,
                                service = selectedService,
                                addons = selectedAddons,
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                subtotal = subtotal,
                                tip = tipAmount,
                                total = total,
                                currency = currency,
                                onPrimaryAction = {}
                            )
                        }
                    }
                }
                BookingStep.Service -> {
                    Div(BookingGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            Row(Modifier.fillMaxWidth().justifyContent(JustifyContent.SpaceBetween), verticalAlignment = Alignment.CenterVertically) {
                                P(BookingTitleStyle.toAttrs()) { Text("Choose a service") }
                                Select(
                                    attrs = BookingDropdownStyle.toModifier().toAttrs {
                                        attr("value", selectedCategory.name)
                                        onChange { event ->
                                            selectedCategory = BookingCategory.valueOf((event.target as HTMLSelectElement).value)
                                        }
                                    }
                                ) {
                                    BookingCategory.values().forEach { category ->
                                        Option(value = category.name) { Text(category.label) }
                                    }
                                }
                            }
                            Div(BookingServiceGridStyle.toAttrs()) {
                                filteredServices.forEach { service ->
                                    Div(
                                        BookingCardStyle.toModifier()
                                            .cursor(Cursor.Pointer)
                                            .onClick {
                                                selectedService = service
                                                selectedAddons.clear()
                                            }
                                            .toAttrs()
                                    ) {
                                        Column(Modifier.gap(0.4.cssRem)) {
                                            P(BookingCardTitleStyle.toAttrs()) { Text(service.name) }
                                            P(BookingCardMetaStyle.toAttrs()) { Text(service.duration) }
                                            Row(Modifier.fillMaxWidth().justifyContent(JustifyContent.FlexEnd)) {
                                                Div(BookingCardMetaStyle.toModifier().fontWeight(FontWeight.Medium).toAttrs()) {
                                                    Text("$currency${service.price}")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            val addonsModifier = if (selectedService != null) {
                                BookingAddonsWrapStyle.toModifier().then(BookingAddonsOpenStyle.toModifier())
                            } else {
                                BookingAddonsWrapStyle.toModifier()
                            }
                            Div(addonsModifier.toAttrs()) {
                                Column(Modifier.gap(1.cssRem)) {
                                    P(BookingTitleStyle.toModifier().fontSize(1.2.cssRem).toAttrs()) { Text("Anything you wish to add?") }
                                    Div(BookingAddonGridStyle.toAttrs()) {
                                        addons.forEach { addon ->
                                            val isSelected = selectedAddons.any { it.name == addon.name }
                                            val modifier = if (isSelected) BookingCardStyle.toModifier().then(BookingCardActiveStyle.toModifier()) else BookingCardStyle.toModifier()
                                            Div(
                                                modifier
                                                    .cursor(Cursor.Pointer)
                                                    .onClick {
                                                        if (isSelected) {
                                                            selectedAddons.removeAll { it.name == addon.name }
                                                        } else {
                                                            selectedAddons.add(addon)
                                                        }
                                                    }
                                                    .toAttrs()
                                            ) {
                                                Column(Modifier.gap(0.35.cssRem)) {
                                                    P(BookingCardTitleStyle.toAttrs()) { Text(addon.name) }
                                                    P(BookingCardMetaStyle.toAttrs()) { Text(addon.duration) }
                                                    Row(Modifier.fillMaxWidth().justifyContent(JustifyContent.FlexEnd)) {
                                                        Div(BookingCardMetaStyle.toModifier().fontWeight(FontWeight.Medium).toAttrs()) {
                                                            Text("$currency${addon.price}")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Div(BookingOrderPanelWrapStyle.toAttrs()) {
                            BookingOrderPanel(
                                step = step,
                                location = selectedLocation,
                                professional = selectedProfessional,
                                service = selectedService,
                                addons = selectedAddons,
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                subtotal = subtotal,
                                tip = tipAmount,
                                total = total,
                                currency = currency,
                                onPrimaryAction = { step = BookingStep.Time }
                            )
                        }
                    }
                }
                BookingStep.Time -> {
                    Div(BookingGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Choose a time") }
                            if (selectedProfessional != null) {
                                Row(Modifier.gap(0.75.cssRem).alignItems(AlignItems.Center)) {
                                    Img(
                                        src = selectedProfessional!!.avatar,
                                        alt = selectedProfessional!!.name,
                                        attrs = Modifier.size(2.6.cssRem).borderRadius(50.percent).toAttrs()
                                    )
                                    Column(Modifier.gap(0.1.cssRem)) {
                                        P(BookingCardTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(selectedProfessional!!.name) }
                                        P(BookingCardMetaStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                            Text(selectedService?.name ?: "Service selection")
                                        }
                                    }
                                }
                            }
                            Column(Modifier.gap(0.9.cssRem)) {
                                Div(BookingCalendarShellStyle.toModifier().toAttrs()) {
                                    Row(BookingCalendarHeaderStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                                        Row(BookingCalendarNavStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                                            Div(BookingCalendarNavButtonStyle.toModifier().cursor(Cursor.Pointer).toAttrs()) { Text("‹") }
                                            Div(BookingCalendarNavButtonStyle.toModifier().cursor(Cursor.Pointer).toAttrs()) { Text("›") }
                                            P(BookingCalendarMonthStyle.toAttrs()) { Text("March 2026") }
                                        }
                                        Div(BookingCalendarTodayStyle.toAttrs()) { Text("Today") }
                                    }
                                    Div(BookingDateRowStyle.toAttrs()) {
                                        dateOptions.forEach { date ->
                                            val isActive = selectedDate == date && !date.isMore
                                            val modifier = if (isActive) {
                                                BookingCalendarDayStyle.toModifier().then(BookingCalendarDayActiveStyle.toModifier())
                                            } else {
                                                BookingCalendarDayStyle.toModifier()
                                            }
                                            Div(
                                                modifier.cursor(Cursor.Pointer).onClick {
                                                    if (!date.isMore) {
                                                        selectedDate = date
                                                    }
                                                }.toAttrs()
                                            ) {
                                                Span(BookingCalendarDayNumberStyle.toAttrs()) { Text(date.number) }
                                                Text(date.label)
                                            }
                                        }
                                    }
                                    Div(BookingTimeRowStyle.toAttrs()) {
                                        timeOptions.forEach { time ->
                                            val isActive = selectedTime == time
                                            val modifier = if (isActive) BookingTimeChipStyle.toModifier().then(BookingTimeChipActiveStyle.toModifier()) else BookingTimeChipStyle.toModifier()
                                            Div(
                                                modifier.cursor(Cursor.Pointer).onClick {
                                                    selectedTime = time
                                                    step = BookingStep.Confirm
                                                }.toAttrs()
                                            ) { Text(time) }
                                        }
                                    }
                                }
                            }
                        }
                        Div(BookingOrderPanelWrapStyle.toAttrs()) {
                            BookingOrderPanel(
                                step = step,
                                location = selectedLocation,
                                professional = selectedProfessional,
                                service = selectedService,
                                addons = selectedAddons,
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                subtotal = subtotal,
                                tip = tipAmount,
                                total = total,
                                currency = currency,
                                onPrimaryAction = { step = BookingStep.Confirm }
                            )
                        }
                    }
                }
                BookingStep.Confirm -> {
                    Div(BookingGridStyle.toAttrs()) {
                        Column(Modifier.gap(1.2.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Confirm booking") }
                            Div(BookingConfirmCardStyle.toAttrs()) {
                                Column(Modifier.gap(0.8.cssRem)) {
                                    P(BookingCardTitleStyle.toAttrs()) { Text("Payment method") }
                                    P(BookingCardMetaStyle.toAttrs()) { Text("Card or bank account") }
                                    Div(BookingDividerStyle.toAttrs())
                                    P(BookingCardTitleStyle.toAttrs()) { Text("Cancellation policy") }
                                    P(BookingCardMetaStyle.toAttrs()) { Text("Cancel or reschedule up to 24 hours before your appointment.") }
                                }
                            }
                        }
                        Div(BookingOrderPanelWrapStyle.toAttrs()) {
                            BookingOrderPanel(
                                step = step,
                                location = selectedLocation,
                                professional = selectedProfessional,
                                service = selectedService,
                                addons = selectedAddons,
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                subtotal = subtotal,
                                tip = tipAmount,
                                total = total,
                                currency = currency,
                                onPrimaryAction = { step = BookingStep.Done }
                            )
                        }
                    }
                }
                BookingStep.Done -> {
                    Div(BookingDoneStyle.toAttrs()) {
                        Column(Modifier.gap(0.7.cssRem)) {
                            P(BookingTitleStyle.toAttrs()) { Text("Booking confirmed") }
                            P(BookingSubtitleStyle.toAttrs()) { Text("Your appointment is set. We can’t wait to see you.") }
                            Button(onClick = { step = BookingStep.Entry }, modifier = BookingPrimaryButtonStyle.toModifier()) {
                                Text("BOOK ANOTHER")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingOrderPanel(
    step: BookingStep,
    location: BookingLocation?,
    professional: BookingProfessional?,
    service: BookingService?,
    addons: List<BookingAddon>,
    selectedDate: BookingCalendarDay,
    selectedTime: String?,
    subtotal: Int,
    tip: Int,
    total: Int,
    currency: String,
    onPrimaryAction: () -> Unit
) {
    Div(BookingOrderPanelStyle.toModifier().toAttrs()) {
        P(BookingOrderTitleStyle.toAttrs()) { Text("Your order") }
        Column(Modifier.gap(0.4.cssRem)) {
            P(BookingCardMetaStyle.toAttrs()) { Text(location?.name ?: "Select a location") }
            if (professional != null) {
                Row(Modifier.gap(0.6.cssRem).alignItems(AlignItems.Center)) {
                    Img(
                        src = professional.avatar,
                        alt = professional.name,
                        attrs = Modifier.size(2.2.cssRem).borderRadius(50.percent).toAttrs()
                    )
                    Column(Modifier.gap(0.1.cssRem)) {
                        P(BookingCardTitleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(professional.name) }
                        P(BookingCardMetaStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(professional.availability) }
                    }
                }
            }
        }
        Div(BookingDividerStyle.toAttrs())
        Column(Modifier.gap(0.4.cssRem)) {
            if (service != null) {
                Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    Text(service.name)
                    Text("$currency${service.price}")
                }
            }
            addons.forEach { addon ->
                Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    Text("+ ${addon.name}")
                    Text("$currency${addon.price}")
                }
            }
            Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                Text("Date")
                Text("${selectedDate.label} ${selectedDate.number}")
            }
            if (!selectedTime.isNullOrBlank()) {
                Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Time")
                    Text(selectedTime)
                }
            }
        }
        Div(BookingDividerStyle.toAttrs())
        Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
            Text("Subtotal")
            Text("$currency${subtotal}")
        }
        if (step == BookingStep.Confirm) {
            Row(BookingOrderRowStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
                Text("Tip")
                Text("$currency${tip}")
            }
            Row(BookingOrderRowStyle.toModifier().fontWeight(FontWeight.Medium), verticalAlignment = Alignment.CenterVertically) {
                Text("Total")
                Text("$currency${total}")
            }
            Column(Modifier.gap(0.6.cssRem)) {
                P(BookingCardMetaStyle.toAttrs()) { Text("Payment options") }
                Column(Modifier.gap(0.5.cssRem)) {
                    Div(
                        BookingPaymentOptionStyle.toModifier()
                            .then(BookingPaymentOptionActiveStyle.toModifier())
                            .toAttrs()
                    ) {
                        Text("VISA •••• 2187")
                        Text("Selected")
                    }
                    Div(BookingPaymentOptionStyle.toAttrs()) {
                        Text("Pay in store")
                        Text("Add")
                    }
                }
            }
        }
        when (step) {
            BookingStep.Service -> {
                if (service != null) {
                    Button(onClick = { onPrimaryAction() }, modifier = BookingPrimaryButtonStyle.toModifier()) {
                        Text("CHOOSE A TIME")
                    }
                }
            }
            BookingStep.Time -> {
                Button(onClick = { onPrimaryAction() }, modifier = BookingPrimaryButtonStyle.toModifier()) {
                    Text("CONFIRM BOOKING")
                }
            }
            BookingStep.Confirm -> {
                Button(onClick = { onPrimaryAction() }, modifier = BookingPrimaryButtonStyle.toModifier()) {
                    Text("BOOK")
                }
            }
            else -> {}
        }
    }
}
