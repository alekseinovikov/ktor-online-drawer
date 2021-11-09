$(document).ready(function(){
    let socket = new WebSocket("ws://localhost:8080/chat");

    let messagesList = $("#messages_div ul");
    let sendButton = $("#send_button");
    let form = $("#form");
    let inputText = $("#message_input");

    socket.onopen = function(e) {
        console.log("Connection established!");
    };

    socket.onclose = function (e) {
        console.log("Connection closed!");
    }

    socket.onmessage = function (event) {
        messagesList.append(`<li>${event.data}</li>`)
    }
    let sendFunction = function (e) {
        e.preventDefault();

        let message = inputText.val();
        socket.send(message);
        inputText.val('');
    };

    sendButton.click(sendFunction);
    form.submit(sendFunction);
});