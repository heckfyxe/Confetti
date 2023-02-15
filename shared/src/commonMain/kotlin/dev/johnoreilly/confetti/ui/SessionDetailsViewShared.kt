package dev.johnoreilly.confetti.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.johnoreilly.confetti.fragment.SessionDetails
import dev.johnoreilly.confetti.fragment.SpeakerDetails
import dev.johnoreilly.confetti.fullNameAndCompany
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
internal fun SessionDetailViewShared(session: SessionDetails?, socialLinkClicked: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Column {
        session?.let { session ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = scrollState)
            ) {

                Text(text = session.title,
                    style = MaterialTheme.typography.h6.copy(letterSpacing = 0.sp),
                    color = Color(0, 128, 255)
                )

                Spacer(modifier = Modifier.size(16.dp))
                Text(text = session.sessionDescription ?: "",
                    style = MaterialTheme.typography.body1.copy(letterSpacing = 0.sp))


                if (session.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.size(16.dp))
                    FlowRow(crossAxisSpacing = 8.dp) {
                        session.tags.forEach { tag ->
                            Chip(tag)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))
                session.speakers.forEach { speaker ->
                    SessionSpeakerInfo(speaker = speaker.speakerDetails, onSocialLinkClick = socialLinkClicked)
                }
            }
        }
    }

}



@Composable
internal fun SessionSpeakerInfo(
    speaker: SpeakerDetails,
    onSocialLinkClick: (String) -> Unit
) {
    //Column(Modifier.padding(top = 16.dp)) {
        Row {

            speaker.photoUrl?.let {
                val painter = rememberAsyncImagePainter(speaker.photoUrl)
                Image(
                    painter, null,
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = speaker.fullNameAndCompany(),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

                speaker.tagline?.let { tagline ->
                    Text(
                        text = tagline,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                }

                speaker.bio?.let { bio ->
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = bio,
                        style = MaterialTheme.typography.body2
                    )
                }


                Row(
                    Modifier.padding(top = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    speaker.socials.forEach { socialsItem ->
                        SocialIcon(
                            modifier = Modifier.size(28.dp),
                            socialItem = socialsItem,
                            onClick = { onSocialLinkClick(socialsItem.url) }
                        )
                    }
                }
            }
        }
    //}
}




@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SocialIcon(
    modifier: Modifier = Modifier,
    resource: String,
    contentDescription: String,
    onClick: () -> Unit
) {
    val iconTint = MaterialTheme.colors.onSurface.copy(alpha = 0.75f)
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier,
            painter = painterResource(resource),
            contentDescription = contentDescription,
            tint = Color(0, 128, 255)
        )
    }
}


@Composable
internal fun SocialIcon(
    modifier: Modifier = Modifier,
    socialItem: SpeakerDetails.Social,
    onClick: () -> Unit
) {
    when (socialItem.name.lowercase()) {
        "github" -> SocialIcon(
            modifier = modifier,
            resource = "github.xml",
            contentDescription = "Github",
            onClick = onClick
        )

        "linkedin" -> SocialIcon(
            modifier = modifier,
            resource = "linkedin.xml",
            contentDescription = "LinkedIn",
            onClick = onClick
        )

        "twitter" -> SocialIcon(
            modifier = modifier,
            resource = "twitter.xml",
            contentDescription = "Twitter",
            onClick = onClick
        )

        "facebook" -> SocialIcon(
            modifier = modifier,
            resource = "facebook.xml",
            contentDescription = "Facebook",
            onClick = onClick
        )

        else -> SocialIcon(
            modifier = modifier,
            resource = "web.xml",
            contentDescription = "Web",
            onClick = onClick
        )
    }
}


@Composable
internal fun Chip(name: String) {
    Surface(
        modifier = Modifier.padding(end = 10.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0, 128, 255)
        //color = MaterialTheme.colors.primary
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(10.dp)
        )
    }
}
