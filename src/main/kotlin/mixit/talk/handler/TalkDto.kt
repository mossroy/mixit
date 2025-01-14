package mixit.talk.handler

import java.time.LocalDateTime
import mixit.talk.model.TalkFormat
import mixit.user.model.Link
import mixit.user.model.User

class TalkDto(
    val id: String?,
    val slug: String,
    val format: TalkFormat,
    val event: String,
    val title: String,
    val summary: String,
    val speakers: List<User>,
    val language: String,
    val addedAt: LocalDateTime,
    val description: String?,
    val topic: String?,
    val video: String?,
    val vimeoPlayer: String?,
    val room: String?,
    val start: String?,
    val end: String?,
    val date: String?,
    val favorite: Boolean = false,
    val photoUrls: List<Link> = emptyList(),
    val isEn: Boolean = (language == "english"),
    val isTalk: Boolean = (format == TalkFormat.TALK),
    val isCurrentEdition: Boolean = "2019".equals(event),
    val multiSpeaker: Boolean = (speakers.size > 1),
    val speakersFirstNames: String = (speakers.joinToString { it.firstname })
)