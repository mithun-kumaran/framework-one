package org.example.app.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.clamp
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
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Span
import org.example.app.components.layouts.PageLayoutData

val HeroGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(3.cssRem)
            .gridTemplateColumns { size(1.fr) }
            .minHeight(70.vh)
    }
    Breakpoint.MD {
        Modifier
            .gridTemplateColumns { size(1.2.fr); size(1.fr) }
            .alignItems(AlignItems.Center)
    }
}

val EyebrowStyle = CssStyle.base {
    Modifier
        .fontSize(0.8.cssRem)
        .letterSpacing(0.28.cssRem)
        .color(Color.rgb(0xB8B8B8))
        .textAlign(TextAlign.Start)
}

val TitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.8.cssRem, 6.5.vw, 4.8.cssRem))
        .fontWeight(FontWeight.Bold)
        .letterSpacing(0.12.cssRem)
        .margin(bottom = 0.05.cssRem)
        .textAlign(TextAlign.Start)
}

val BookButtonStyle = CssStyle {
    base {
        Modifier
            .padding(leftRight = 1.5.cssRem, topBottom = 0.55.cssRem)
            .border(1.px, LineStyle.Solid, Color.rgb(0xC59A3E))
            .borderRadius(0.6.cssRem)
            .backgroundColor(Colors.Transparent)
            .boxShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 14.px, color = Color.rgba(197, 154, 62, 0.35f))
            .color(Color.rgb(0xF5F5F5))
            .fontWeight(FontWeight.SemiBold)
            .fontSize(0.75.cssRem)
            .letterSpacing(0.18.cssRem)
    }
    cssRule(":hover") {
        Modifier
            .backgroundColor(Color.rgb(0xC59A3E))
            .color(Color.rgb(0x0B0B0D))
    }
}

val SecondaryButtonStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.5.cssRem, topBottom = 0.55.cssRem)
        .borderRadius(0.6.cssRem)
        .backgroundColor(Color.rgb(0xF5F5F5))
        .color(Color.rgb(0x0B0B0D))
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.75.cssRem)
        .letterSpacing(0.16.cssRem)
}

val HeroSubtextStyle = CssStyle.base {
    Modifier
        .fontSize(0.7.cssRem)
        .letterSpacing(0.1.cssRem)
        .color(Color.rgb(0xD1D1D1))
        .textAlign(TextAlign.Start)
}

val ServicePanelStyle = CssStyle.base {
    Modifier
        .padding(1.8.cssRem)
        .backgroundColor(Colors.Transparent)
        .width(clamp(18.cssRem, 32.vw, 32.cssRem))
}

val ServicesGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(1.6.cssRem, 3.5.vw, 2.6.cssRem))
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr) }
    }
    Breakpoint.LG {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr); size(1.fr) }
    }
}

val ServiceCardStyle = CssStyle.base {
    Modifier
        .backgroundColor(Color.rgb(0x0D0D0D))
        .borderRadius(1.2.cssRem)
        .padding(1.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0x1F1F1F))
        .boxShadow(offsetX = 0.px, offsetY = 18.px, blurRadius = 40.px, color = Color.rgba(0, 0, 0, 0.35f))
}

val ServiceImageWrapStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(12.5.cssRem)
        .borderRadius(1.cssRem)
        .position(Position.Relative)
}

val ServiceTitleStyle = CssStyle.base {
    Modifier
        .fontSize(0.95.cssRem)
        .letterSpacing(0.14.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val ServicePriceStyle = CssStyle.base {
    Modifier
        .fontSize(0.95.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .letterSpacing(0.08.cssRem)
}

val ServiceDescriptionStyle = CssStyle.base {
    Modifier
        .fontSize(0.7.cssRem)
        .color(Color.rgb(0xB8B8B8))
        .lineHeight(1.4)
}

val ServiceOverlayStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(0.px)
        .left(0.px)
        .right(0.px)
        .bottom(0.px)
        .borderRadius(1.cssRem)
        .backgroundColor(Color.rgba(0, 0, 0, 0.6f))
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
}

val ServiceOverlayButtonStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.3.cssRem, topBottom = 0.45.cssRem)
        .borderRadius(0.6.cssRem)
        .backgroundColor(Color.rgb(0xC59A3E))
        .color(Color.rgb(0x0B0B0B))
        .fontWeight(FontWeight.SemiBold)
        .letterSpacing(0.18.cssRem)
        .fontSize(0.7.cssRem)
}

val GalleryTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.6.cssRem, 3.2.vw, 2.4.cssRem))
        .letterSpacing(0.28.cssRem)
        .textAlign(TextAlign.Center)
        .fontWeight(FontWeight.SemiBold)
}

val GallerySubtitleStyle = CssStyle.base {
    Modifier
        .fontSize(0.7.cssRem)
        .letterSpacing(0.08.cssRem)
        .color(Color.rgb(0xB8B8B8))
        .textAlign(TextAlign.Center)
}

val GalleryGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(0.9.cssRem, 2.2.vw, 1.4.cssRem))
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr) }
    }
    Breakpoint.LG {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr); size(1.fr); size(1.fr) }
    }
}

val GalleryCardStyle = CssStyle.base {
    Modifier
        .borderRadius(0.9.cssRem)
        .overflow(Overflow.Hidden)
        .backgroundColor(Color.rgb(0x0D0D0D))
}

val ServiceRowStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 1.1.cssRem)
        .borderBottom(1.px, LineStyle.Solid, Color.rgb(0x1F1F1F))
}

val ServiceNameStyle = CssStyle.base {
    Modifier.fontSize(1.05.cssRem).letterSpacing(0.08.cssRem)
}

val PriceStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.SemiBold)
}

val MiniButtonStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 1.2.cssRem, topBottom = 0.4.cssRem)
        .borderRadius(0.5.cssRem)
        .backgroundColor(Color.rgb(0xC59A3E))
        .color(Color.rgb(0x0B0B0D))
        .fontWeight(FontWeight.SemiBold)
        .letterSpacing(0.12.cssRem)
}

val ServiceActionWrapStyle = CssStyle.base {
    Modifier
        .minWidth(9.cssRem)
        .height(2.6.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.FlexEnd)
}

val SectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 5.cssRem)
        .borderBottom(1.px, LineStyle.Solid, Color.rgb(0x1F1F1F))
}

val SectionTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.6.cssRem, 3.5.vw, 2.4.cssRem))
        .fontWeight(FontWeight.SemiBold)
        .letterSpacing(0.12.cssRem)
        .margin(bottom = 1.2.cssRem)
}

val SectionTextStyle = CssStyle.base {
    Modifier
        .color(Color.rgb(0xB8B8B8))
        .fontSize(1.05.cssRem)
}

val MoreAboutTextStyle = CssStyle.base {
    Modifier
        .letterSpacing(0.32.cssRem)
        .fontWeight(FontWeight.Medium)
        .textAlign(TextAlign.Center)
}

val MoreAboutCircleStyle = CssStyle.base {
    Modifier
        .width(3.2.cssRem)
        .height(3.2.cssRem)
        .border(1.px, LineStyle.Solid, Color.rgb(0xF5F5F5))
        .borderRadius(50.percent)
        .backgroundColor(Colors.Transparent)
        .color(Color.rgb(0xF5F5F5))
        .fontSize(1.35.cssRem)
        .fontWeight(FontWeight.Bold)
        .padding(0.px)
}

val AboutSectionGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(clamp(2.cssRem, 6.vw, 4.5.cssRem))
            .gridTemplateColumns { size(1.fr) }
            .alignItems(AlignItems.Center)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { size(1.fr); size(1.fr) }
    }
}

val AboutTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.2.cssRem, 5.4.vw, 3.2.cssRem))
        .letterSpacing(0.2.cssRem)
        .fontWeight(FontWeight.Bold)
        .lineHeight(1.1)
        .textAlign(TextAlign.Start)
}

val AboutTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.8.cssRem)
        .letterSpacing(0.08.cssRem)
        .color(Color.rgb(0xB8B8B8))
        .lineHeight(1.6)
        .textAlign(TextAlign.Start)
}

val AboutStatsRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(clamp(2.2.cssRem, 6.vw, 4.5.cssRem))
        .alignItems(AlignItems.Center)
}

val AboutStatValueStyle = CssStyle.base {
    Modifier
        .fontSize(1.6.cssRem)
        .fontWeight(FontWeight.Bold)
        .letterSpacing(0.1.cssRem)
}

val AboutStatAccentStyle = CssStyle.base {
    Modifier.color(Color.rgb(0xC59A3E))
}

val AboutStatLabelStyle = CssStyle.base {
    Modifier
        .fontSize(0.6.cssRem)
        .letterSpacing(0.14.cssRem)
        .color(Color.rgb(0xB8B8B8))
}

val AboutImageStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .height(clamp(16.cssRem, 28.vw, 22.cssRem))
        .borderRadius(1.2.cssRem)
        .boxShadow(offsetX = 0.px, offsetY = 18.px, blurRadius = 40.px, color = Color.rgba(0, 0, 0, 0.4f))
}

val ReviewRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(1.2.cssRem)
}

val ReviewCardStyle = CssStyle.base {
    Modifier
        .padding(0.85.cssRem)
        .borderRadius(1.2.cssRem)
        .backgroundColor(Color.rgba(24, 24, 24, 0.45f))
        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.12f))
        .boxShadow(offsetX = 0.px, offsetY = 10.px, blurRadius = 28.px, color = Color.rgba(0, 0, 0, 0.5f))
        .width(clamp(12.cssRem, 16.vw, 16.cssRem))
}

val ReviewNameStyle = CssStyle.base {
    Modifier.fontWeight(FontWeight.SemiBold).fontSize(0.8.cssRem)
}

val ReviewRoleStyle = CssStyle.base {
    Modifier.fontSize(0.6.cssRem).color(Color.rgb(0xB8B8B8))
}

val ReviewTextStyle = CssStyle.base {
    Modifier.fontSize(0.65.cssRem).color(Color.rgb(0xD3D3D3)).lineHeight(1.35)
}

val ReviewStarsStyle = CssStyle.base {
    Modifier.fontSize(0.7.cssRem).color(Color.rgb(0xC59A3E))
}

val ReviewNavButtonStyle = CssStyle.base {
    Modifier
        .padding(0.px)
        .backgroundColor(Colors.Transparent)
        .color(Color.rgb(0xF5F5F5))
        .fontSize(1.4.cssRem)
}

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Book Now"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    Column(Modifier.fillMaxWidth().gap(4.cssRem).id("about")) {
        Div(HeroGridStyle.toAttrs()) {
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Column(Modifier.gap(1.2.cssRem).alignSelf(AlignSelf.Center).padding(top = 1.4.cssRem)) {
                    Column(Modifier.gap(0.1.cssRem)) {
                        Div(EyebrowStyle.toAttrs()) { Text("BARBER SHOP KRAKOV") }
                        H1(TitleStyle.toAttrs()) { Text("PEAKY BLADES") }
                        P(HeroSubtextStyle.toAttrs()) {
                            Text("Premium Cuts from £25 - in Huntingdon Valley, Philadelphia")
                        }
                    }
                    Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { window.location.href = "/book" }, modifier = BookButtonStyle.toModifier()) {
                            Text("BOOK NOW  \u2192")
                        }
                        Button(onClick = { window.location.hash = "footer" }, modifier = SecondaryButtonStyle.toModifier()) {
                            Text("VIEW LOCATIONS")
                        }
                    }
                }
                ReviewCarousel(Modifier.alignSelf(AlignSelf.Start))
            }
            Column(
                Modifier.fillMaxHeight().padding(top = 3.cssRem),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Div(ServicePanelStyle.toModifier().toAttrs()) {
                    ServiceRow("Haircuts", "£25")
                    ServiceRow("Beard Trimming", "£15")
                    ServiceRow("Shaving", "£20")
                    ServiceRow("Hair Styling", "£30")
                    ServiceRow("Facial Treatments", "£35")
                    ServiceRow("Grooming Packages", "£40")
                }
            }
        }

        Column(Modifier.fillMaxWidth().gap(0.6.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
            P(MoreAboutTextStyle.toAttrs()) { Text("MORE ABOUT US") }
            Button(onClick = { window.location.hash = "about-us" }, modifier = MoreAboutCircleStyle.toModifier()) {
                Text("\u2193")
            }
        }

        Div(SectionStyle.toModifier().id("about-us").toAttrs()) {
            Div(AboutSectionGridStyle.toAttrs()) {
                Column(Modifier.gap(1.2.cssRem)) {
                    P(AboutTitleStyle.toAttrs()) {
                        Text("YOUR PERSONAL\nBARBER SERVICE\nANY TIME")
                    }
                    P(AboutTextStyle.toAttrs()) {
                        Text("Located in the Chinatown NYC, 12 Pell is a lifestyle store dedicated to personal care and style. We aim to redefine and reinvent the standard retail and grooming experience. 12 Pell is a place for relaxation and leisure.")
                    }
                    Row(AboutStatsRowStyle.toModifier()) {
                        Column(Modifier.gap(0.1.cssRem)) {
                            P(AboutStatValueStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                Text("99")
                                Span(AboutStatAccentStyle.toAttrs()) { Text("%") }
                            }
                            P(AboutStatLabelStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("CUSTOMER\nSATISFACTION") }
                        }
                        Column(Modifier.gap(0.1.cssRem)) {
                            P(AboutStatValueStyle.toModifier().margin(bottom = 0.px).toAttrs()) {
                                Text("10")
                                Span(AboutStatAccentStyle.toAttrs()) { Text("+") }
                            }
                            P(AboutStatLabelStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text("YEARS OF\nEXPERIENCE") }
                        }
                    }
                }
                Img(
                    src = "/ABOUT%20US.jpg",
                    alt = "About us",
                    attrs = AboutImageStyle.toModifier().toAttrs {
                        style { property("object-fit", "cover") }
                    }
                )
            }
        }

        Div(SectionStyle.toModifier().id("services").toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(1.6.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
                P(GalleryTitleStyle.toAttrs()) { Text("OUR SERVICES") }
                Div(ServicesGridStyle.toAttrs()) {
                    ServiceCard(
                        ServiceItem(
                            title = "Haircuts",
                            price = "£25",
                            description = "Precision cut tailored to your style and face shape, finished with a sharp, clean look.",
                            image = "/service1.jpg"
                        )
                    )
                    ServiceCard(
                        ServiceItem(
                            title = "Beard Trimming",
                            price = "£15",
                            description = "Expert beard shaping and detailing to keep your lines crisp and well-groomed.",
                            image = "/service2.jpg"
                        )
                    )
                    ServiceCard(
                        ServiceItem(
                            title = "Hair Styling",
                            price = "£20",
                            description = "Professional styling using premium products for volume, texture, and long-lasting hold.",
                            image = "/service3.jpg"
                        )
                    )
                    ServiceCard(
                        ServiceItem(
                            title = "Facial Treatments",
                            price = "£30",
                            description = "Deep cleanse and revitalizing treatment to refresh your skin and reduce irritation.",
                            image = "/service4.jpg"
                        )
                    )
                    ServiceCard(
                        ServiceItem(
                            title = "Shaving",
                            price = "£35",
                            description = "Traditional close shave with hot towel prep for a smooth, refreshed finish.",
                            image = "/service5.jpg"
                        )
                    )
                    ServiceCard(
                        ServiceItem(
                            title = "Grooming Packages",
                            price = "£40",
                            description = "Complete grooming experience, combining haircut, beard work, and finishing touches for a polished look.",
                            image = "/service6.jpg"
                        )
                    )
                }
            }
        }

        Div(SectionStyle.toModifier().padding(topBottom = 3.cssRem).id("gallery").toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(1.6.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
                Column(Modifier.gap(0.4.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
                    P(GalleryTitleStyle.toAttrs()) { Text("OUR GALLERY") }
                    P(GallerySubtitleStyle.toAttrs()) { Text("Real results. Clean transformations from before to after.") }
                }
                Div(GalleryGridStyle.toAttrs()) {
                    listOf(
                        "/before1.jpeg",
                        "/before2.jpeg",
                        "/before3.jpeg",
                        "/before4.jpeg",
                        "/before5.jpeg",
                        "/before6.jpeg",
                        "/after1.jpeg",
                        "/after2.jpeg",
                        "/after3.jpeg",
                        "/after4.jpeg",
                        "/after5.jpeg",
                        "/after6.jpeg"
                    ).forEach { image ->
                        Div(GalleryCardStyle.toAttrs()) {
                            Img(
                                src = image,
                                alt = "Gallery photo",
                                attrs = Modifier
                                    .fillMaxWidth()
                                    .height(clamp(14.cssRem, 24.vw, 17.5.cssRem))
                                    .toAttrs {
                                        style {
                                            property("object-fit", "cover")
                                            property("object-position", "center")
                                            property("transform", "scale(1.03)")
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun ServiceRow(name: String, price: String?) {
    val showBook = remember { mutableStateOf(false) }
    Row(
        ServiceRowStyle.toModifier()
            .onMouseEnter { showBook.value = true }
            .onMouseLeave { showBook.value = false },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        P(ServiceNameStyle.toAttrs()) { Text(name) }
        Div(ServiceActionWrapStyle.toModifier().toAttrs()) {
            if (showBook.value) {
                Button(onClick = { window.location.href = "/book" }, modifier = MiniButtonStyle.toModifier()) {
                    Text("BOOK  \u2192")
                }
            } else if (price != null) {
                P(PriceStyle.toAttrs()) { Text(price) }
            }
        }
    }
}

private data class Review(val name: String, val role: String, val text: String, val pfp: String)
private data class ServiceItem(val title: String, val price: String, val description: String, val image: String)

@Composable
private fun ServiceCard(service: ServiceItem) {
    var isHovered by remember { mutableStateOf(false) }
    Column(
        ServiceCardStyle.toModifier()
            .onMouseEnter { isHovered = true }
            .onMouseLeave { isHovered = false }
            .gap(0.8.cssRem)
    ) {
        Box(ServiceImageWrapStyle.toModifier()) {
            Img(
                src = service.image,
                alt = service.title,
                attrs = Modifier
                    .fillMaxSize()
                    .borderRadius(1.cssRem)
                    .opacity(if (isHovered) 0.45 else 1.0)
                    .transition {
                        property("opacity")
                        duration(220.ms)
                        timingFunction(AnimationTimingFunction.Ease)
                    }
                    .toAttrs {
                        style { property("object-fit", "cover") }
                    }
            )
            if (isHovered) {
                Div(ServiceOverlayStyle.toAttrs()) {
                    Button(
                        onClick = { window.location.href = "/book" },
                        modifier = ServiceOverlayButtonStyle.toModifier()
                    ) {
                        Text("BOOK  \u2192")
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            P(ServiceTitleStyle.toAttrs()) { Text(service.title) }
            P(ServicePriceStyle.toAttrs()) { Text(service.price) }
        }
        P(ServiceDescriptionStyle.toAttrs()) { Text(service.description) }
    }
}

@Composable
private fun ReviewCarousel(modifier: Modifier = Modifier) {
    val reviews = listOf(
        Review("Medite Ranija", "Business Man", "Best trim I\u2019ve had in a long time. Clean, precise, and fast.", "/pfp1.jfif"),
        Review("Klara Ivanov", "Product Designer", "Sharp cut and easy booking. The service feels premium.", "/pfp2.jfif"),
        Review("Aiden Brooks", "Photographer", "Great attention to detail and perfect beard shaping.", "/pfp3.jfif"),
        Review("Noah Grant", "Entrepreneur", "Friendly team and a classic finish every single time.", "/pfp4.jfif"),
        Review("Luca Marin", "Architect", "Cool space and consistent results. I keep coming back.", "/pfp5.jfif"),
        Review("Maya Patel", "Marketing Lead", "Quick, clean, and confident cut. Exactly what I wanted.", "/pfp6.jfif")
    )
    var startIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4500)
            startIndex = (startIndex + 1) % reviews.size
        }
    }

    Row(ReviewRowStyle.toModifier().then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = { startIndex = (startIndex - 1 + reviews.size) % reviews.size },
            modifier = ReviewNavButtonStyle.toModifier()
        ) {
            Text("\u2039")
        }
        Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.CenterVertically) {
            repeat(3) { offset ->
                val review = reviews[(startIndex + offset) % reviews.size]
                ReviewCard(review)
            }
        }
        Button(
            onClick = { startIndex = (startIndex + 1) % reviews.size },
            modifier = ReviewNavButtonStyle.toModifier()
        ) {
            Text("\u203A")
        }
    }
}

@Composable
private fun ReviewCard(review: Review) {
    Div(ReviewCardStyle.toModifier().toAttrs()) {
        Row(Modifier.gap(0.6.cssRem).alignItems(AlignItems.Center)) {
            Img(
                src = review.pfp,
                alt = review.name,
                attrs = Modifier.size(2.1.cssRem).borderRadius(50.percent).border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.2f)).toAttrs()
            )
            Column(Modifier.gap(0.1.cssRem)) {
                P(ReviewNameStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.name) }
                P(ReviewRoleStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.role) }
            }
        }
        P(ReviewStarsStyle.toModifier().margin(topBottom = 0.35.cssRem).toAttrs()) { Text("\u2605\u2605\u2605\u2605\u2605") }
        P(ReviewTextStyle.toModifier().margin(bottom = 0.px).toAttrs()) { Text(review.text) }
    }
}
