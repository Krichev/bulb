<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>

        body {
            background-color: bisque;

        }

        h1 {
            font-family: Verdana, Geneva, Arial, sans-serif;
            font-size: 40px;
            font-weight: bolder;
            color: black;
            text-align: center;


        }

        .door {
            display: inline-block;
            padding: 50px 50px 50px 50px;
            margin: 50px 50px 50px 50px;
            left: 50px;
            border: 3px solid darkmagenta;
        }

        #image {

            display: inline-block;
            padding: 5px 5px 5px 5px;
            border: 3px solid darkmagenta;
            vertical-align: middle;
            position: fixed;
            left: 500px;
            text-align: center;
        }


        #button {
            cursor: grabbing;
            font-size: 30px;
            font-weight: bold;
            text-align: center;
            font-variant: small-caps;
            color: white;
            position: relative;
            top: 300px;
            left: 850px;
            background-color: tomato;
        }


    </style>
    <script >
        let SwitcherUnit = {
            init() {
                this.photo = document.getElementsByClassName("door");
                this.button = document.getElementById("button");
                this.doors = document.getElementById("AllDoors");
                this.image = document.getElementById("image");
                this.openSocket();
                this.bindEvents();
                this.return();
            },
            bindEvents() {

                for (let i = 0; i < this.photo.length; i++) {
                    let altWith = this.photo[i].getAttribute("alt").concat(0);
                    this.photo[i].addEventListener("click", e => this.ws.send(altWith));
                }
                this.doors.addEventListener("click", evt => this.doors.style.display = 'none');
                this.doors.addEventListener("click", evt => this.button.style.display = 'block')
                this.doors.addEventListener("click", evt => this.image.style.display = 'block')
                for (let i = 0; i < this.photo.length; i++) {
                    this.photo[i].addEventListener("click", e => this.image.setAttribute("alt", this.photo[i].getAttribute("alt")));
                }
                this.image.addEventListener("click", e => this.ws.send(this.image.getAttribute("alt")));
            },

            onOpenSock() {
                alert("Opened connection to webSocket");
            },
            onMessage(evt) {
                let bytes = new Uint8Array(evt.data);
                let data = "";
                let len = bytes.byteLength;
                for (let i = 0; i < len; ++i) {
                    data += String.fromCharCode(bytes[i]);
                }
                this.image.src = "data:image/png;base64," + window.btoa(data);
            },
            onClose() {
                alert("Closed");
            },

            openSocket() {
                this.ws = new WebSocket("ws://localhost:8080/WebS/image");
                this.ws.onopen = () => this.onOpenSock();
                this.ws.onmessage = (e) => this.onMessage(e);
                this.ws.onclose = () => this.onClose();
                this.ws.binaryType = "arraybuffer";

            },
            return() {
                this.button.addEventListener("click", evt => this.image.style.display = 'none');
                this.button.addEventListener("click", evt => this.doors.style.display = 'block');
                this.button.addEventListener("click", evt => this.button.style.display = 'none');
            }
        };

        window.addEventListener("load", e => SwitcherUnit.init());

    </script>
</head>
<body>
<h1>Door</h1>
<h1 class="text">Door Selection Page</h1>

<div id="AllDoors">

    <img src="https://cloclo23.datacloudmail.ru/view/door.png?etag=59CC9C15AE68A08204675A9DEB87F58885BC7F94&x-email=alen_melkonyan%40mail.ru" alt="1" class="door">
    <img src="https://cloclo23.datacloudmail.ru/view/door.png?etag=59CC9C15AE68A08204675A9DEB87F58885BC7F94&x-email=alen_melkonyan%40mail.ru" alt="2" class="door">
    <img src="https://cloclo23.datacloudmail.ru/view/door.png?etag=59CC9C15AE68A08204675A9DEB87F58885BC7F94&x-email=alen_melkonyan%40mail.ru" alt="3" class="door">
    <img src="https://cloclo23.datacloudmail.ru/view/door.png?etag=59CC9C15AE68A08204675A9DEB87F58885BC7F94&x-email=alen_melkonyan%40mail.ru" alt="4" class="door">
    <img src="https://cloclo23.datacloudmail.ru/view/door.png?etag=59CC9C15AE68A08204675A9DEB87F58885BC7F94&x-email=alen_melkonyan%40mail.ru" alt="5" class="door">


</div>

<div class="bulb">
    <img id="image" style="display: none">
</div>
<button id="button" type="button" style="display: none"> Back to doors</button>
</body>
</html>