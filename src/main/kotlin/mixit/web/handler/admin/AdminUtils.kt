package mixit.web.handler.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mixit.model.Link
import mixit.web.handler.admin.AdminUtils.toLinks
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

object AdminUtils {

    fun admin(req: ServerRequest) =
        ServerResponse.ok().render("admin", mapOf(Pair("title", "admin.title")))

    fun Any.toJson(objectMapper: ObjectMapper): String =
        objectMapper.writeValueAsString(this).replace("\"", "&quot;")

    fun String.toLinks(objectMapper: ObjectMapper): List<Link> =
        if (this.isEmpty()) emptyList() else objectMapper.readValue(this)

    fun String.toLink(objectMapper: ObjectMapper): Link? =
        if (this.isEmpty()) null else objectMapper.readValue(this)
}