$(document).ready(function(){
    let socket = new WebSocket("ws://localhost:8080/draw");

    let canvas = document.getElementById("canvas");
    let ctx = canvas.getContext("2d");
    let prevPosition = null;

    socket.onopen = function (e) {
        console.log("Connection established!");
    };

    socket.onclose = function (e) {
        console.log("Connection closed!");
    }

    function draw(coord) {
        ctx.beginPath();
        ctx.lineWidth = 5;
        ctx.lineCap = "round";
        ctx.strokeStyle = "#090606";

        if (coord.x1) {
            ctx.moveTo(coord.x1, coord.y1);
        } else {
            ctx.moveTo(coord.x2, coord.y2);
        }

        ctx.lineTo(coord.x2, coord.y2);
        ctx.stroke();
    }

    function sendToServer(event) {
        let x = event.clientX;
        let y = event.clientY;

        let prevX = null;
        let prevY = null;
        if (prevPosition) {
            prevX = prevPosition.x;
            prevY = prevPosition.y;

            if (prevX === x && prevY === y) {
                return;
            }
        }

        socket.send(JSON.stringify({x2: x, y2: y, x1: prevX, y1: prevY}));

        prevPosition = {x: x, y: y};
    }

    function start() {
        document.addEventListener("mousemove", sendToServer);
    }

    function stop() {
        document.removeEventListener("mousemove", sendToServer);
        prevPosition = null;
    }

    function resize() {
        ctx.canvas.width = window.innerWidth;
        ctx.canvas.height = window.innerHeight;
    }

    resize();

    socket.onmessage = function (event) {
        draw(JSON.parse(event.data));
    }
    document.addEventListener("mousedown", start);
    document.addEventListener("mouseup", stop);
    window.addEventListener("resize", resize);
});