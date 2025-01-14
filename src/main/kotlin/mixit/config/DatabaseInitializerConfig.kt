package mixit.config

import mixit.blog.model.BlogService
import mixit.blog.repository.PostRepository
import mixit.event.model.EventService
import mixit.event.repository.EventRepository
import mixit.favorite.repository.FavoriteRepository
import mixit.talk.model.TalkService
import mixit.talk.repository.TalkRepository
import mixit.ticket.repository.FinalTicketRepository
import mixit.ticket.repository.TicketRepository
import mixit.user.model.UserService
import mixit.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseInitializerConfig {

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        eventRepository: EventRepository,
        talkRepository: TalkRepository,
        ticketRepository: TicketRepository,
        postRepository: PostRepository,
        favoriteRepository: FavoriteRepository,
        finalTicketRepository: FinalTicketRepository,
        eventService: EventService,
        blogService: BlogService,
        talkService: TalkService,
        userService: UserService
    ) = CommandLineRunner {

        userRepository.initData()
        eventRepository.initData()
        talkRepository.initData()
        postRepository.initData()
        ticketRepository.initData()
        favoriteRepository.initData()
        finalTicketRepository.initData()

        userService.initializeCache()
        eventService.initializeCache()
        blogService.initializeCache()
        talkService.initializeCache()
    }
}
