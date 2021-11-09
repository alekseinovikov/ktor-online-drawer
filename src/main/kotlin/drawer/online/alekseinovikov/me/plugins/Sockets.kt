package drawer.online.alekseinovikov.me.plugins

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.client.features.json.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.SendChannel
import java.time.Duration
import java.util.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val connections: MutableSet<SendChannel<Frame>> = mutableSetOf()
    val history = LinkedList<Coords>()
    val serializer = jacksonObjectMapper()

    routing {
        webSocket("/draw") { // websocketSession
            history.forEach { outgoing.send(Frame.Text(serializer.writeValueAsString(it))) }
            connections.add(outgoing)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val coords = serializer.readValue(receivedText, Coords::class.java)
                    history.add(coords)
                    connections.forEach { it.send(Frame.Text(receivedText)) }
                }
            } finally {
                connections.remove(outgoing)
            }
        }
    }
}

data class Coords(val x1: Int?, val x2: Int, val y1: Int?, val y2: Int)
