package dev.johnoreilly.confetti.ui

import androidx.compose.runtime.Composable
import dev.johnoreilly.confetti.fragment.SessionDetails
import dev.johnoreilly.confetti.fragment.SpeakerDetails
import dev.johnoreilly.confetti.ui.SessionDetailViewShared

@Composable
fun SessionDetailViewSharedWrapper(session: SessionDetails?, socialLinkClicked: (String) -> Unit) {
    SessionDetailViewShared(session, socialLinkClicked)
}

@Composable
fun SessionSpeakerInfoViewSharedWrapper(speaker: SpeakerDetails, socialLinkClicked: (String) -> Unit) {
    SessionSpeakerInfo(speaker, socialLinkClicked)
}
