package drawer.online.alekseinovikov.me.plugins

import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.coroutines.channels.SendChannel

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val connections: MutableSet<SendChannel<Frame>> = mutableSetOf()

    routing {
        webSocket("/chat") { // websocketSession
            send("Welcome to the chat!")
            connections.add(outgoing)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    connections.forEach { it.send(Frame.Text(receivedText)) }
                }
            }finally {
                connections.remove(outgoing)
            }
        }
    }
}
