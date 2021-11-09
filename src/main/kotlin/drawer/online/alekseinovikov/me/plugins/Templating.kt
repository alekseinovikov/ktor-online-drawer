package drawer.online.alekseinovikov.me.plugins

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.configureTemplating() {

    routing {
        get("/") {
            call.respondHtml {
                head {
                    script {
                        src = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"
                    }
                    script {
                        src = "/static/js/index.js"
                    }
                }
                body {
                    canvas {
                        id = "canvas"
                        style = "width: 100vw;" +
                                "height: 100vh;"
                    }
                }
            }
        }
    }
}

