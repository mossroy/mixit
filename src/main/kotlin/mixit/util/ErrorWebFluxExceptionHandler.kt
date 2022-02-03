package mixit.util

import java.util.Locale
import mixit.MixitProperties
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.ResourceProperties
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.MessageSource
import org.springframework.web.reactive.function.server.RenderingResponse
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toMono

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 13/12/17.
 */
class ErrorWebFluxExceptionHandler(
    errorAttributes: ErrorAttributes,
    resourceProperties: ResourceProperties,
    errorProperties: ErrorProperties,
    applicationContext: ApplicationContext,
    private val messageSource: MessageSource,
    private val properties: MixitProperties
) :
    DefaultErrorWebExceptionHandler(errorAttributes, resourceProperties, errorProperties, applicationContext) {

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return super.getRoutingFunction(errorAttributes)
            .filter { request, next ->
                val locale: Locale = request.locale()
                val path = request.uri().path
                request.session().flatMap { session ->
                    val model = generateModel(properties, path, locale, session, messageSource)
                    next.handle(request)
                        .flatMap { if (it is RenderingResponse) RenderingResponse.from(it).modelAttributes(model).build() else it.toMono() }
                }
            }
    }
}