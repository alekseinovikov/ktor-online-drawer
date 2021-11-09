package drawer.online.alekseinovikov.me

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import drawer.online.alekseinovikov.me.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSockets()
        configureTemplating()
    }.start(wait = true)
}
