package dev.johnoreilly.confetti.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.window.ComposeUIViewController
import dev.johnoreilly.confetti.fragment.SessionDetails
import dev.johnoreilly.confetti.fragment.SpeakerDetails
import platform.UIKit.UIViewController

fun SessionDetailsViewController(session: SessionDetails, socialLinkClicked: (String) -> Unit): UIViewController =
    ComposeUIViewController {
        MaterialTheme {
            SessionDetailViewShared(session, socialLinkClicked)
        }
    }


fun SessionSpeakerInfoViewController(
    speaker: SpeakerDetails,
    onSocialLinkClicked: (String) -> Unit,
    heightChanged: (Int) -> Unit
) = ComposeUIViewController {
    MaterialTheme {
        Column(modifier = Modifier
            .onGloballyPositioned { coordinates ->
                heightChanged(coordinates.size.height)
            }
        ) {
            SessionSpeakerInfo(speaker, onSocialLinkClicked)
        }
    }
}

