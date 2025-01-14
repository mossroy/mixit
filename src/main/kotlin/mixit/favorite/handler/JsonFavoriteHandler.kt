package mixit.favorite.handler

import mixit.favorite.model.Favorite
import mixit.favorite.repository.FavoriteRepository
import mixit.security.model.Cryptographer
import mixit.util.json
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
class JsonFavoriteHandler(private val favoriteRepository: FavoriteRepository, private val cryptographer: Cryptographer) {

    fun findAll(req: ServerRequest) =
        ok().json().body(favoriteRepository.findAll())

    fun toggleFavorite(req: ServerRequest) =
        ok().json().body(
            favoriteRepository.findByEmailAndTalk(req.pathVariable("email"), req.pathVariable("id"))
                // if favorite is found we delete it
                .flatMap {
                    favoriteRepository.delete(req.pathVariable("email"), it.talkId)
                        .map { FavoriteDto(req.pathVariable("id"), false) }
                }
                // otherwise we create it
                .switchIfEmpty(
                    Mono.defer {
                        favoriteRepository.save(
                            Favorite(
                                cryptographer.encrypt(
                                    req.pathVariable("email")
                                )!!,
                                req.pathVariable("id")
                            )
                        ).map { FavoriteDto(it.talkId, true) }
                    }
                )
        )

    fun getFavorite(req: ServerRequest) =
        ok().json().body(
            favoriteRepository.findByEmailAndTalk(req.pathVariable("email"), req.pathVariable("id"))
                .flatMap { FavoriteDto(it.talkId, true).toMono() }
                .switchIfEmpty(FavoriteDto(req.pathVariable("id"), false).toMono())
        )

    fun getFavorites(req: ServerRequest) =
        ok().json().body(favoriteRepository.findByEmail(req.pathVariable("email")))
}
