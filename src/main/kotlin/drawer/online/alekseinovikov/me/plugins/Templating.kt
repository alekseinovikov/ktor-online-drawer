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
                    h1 { +"Online chat!" }
                    div {
                        id = "messages_div"

                        ul {
                        }
                    }

                    div {
                        id = "send_div"

                        form {
                            id = "form"

                            input {
                                id = "message_input"
                                type = InputType.text
                                placeholder = "Enter your message"
                            }
                            button {
                                id = "send_button"
                                type = ButtonType.button
                                +"Send"
                            }
                        }
                    }
                }
            }
        }
    }
}

