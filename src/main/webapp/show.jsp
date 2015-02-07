<%--
  Created by IntelliJ IDEA.
  User: Mark
  Date: 2015/2/6
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message</title>
    <link type="text/css" href="resource/css/main.css" rel="stylesheet" />
    <style type="text/css">
        #verify label {
            width: 100%;
            display: block;
            text-align: center;
        }
        #verify input[type="password"] {
            height: 25px;
            width: 300px;
            font-size: 20px;
            display: block;
            margin: 20px auto;
            border-radius: 3px;
        }
        .delete {
            padding:10px 15px;
            font-size: 20px !important;
        }
        #msg {
            display: none;
        }

    </style>
</head>
<body>
    <header>
        查看信息
    </header>
    <div id="main">
        <div id="verify">
            <label for="password">请输入密码</label>
            <input id="password" type="password" name="password" />
            <a id="submit" href="#" class="show">查看</a>
        </div>
        <div id="msg">
            <input type="hidden" id="hid" />
            <span id="txt">people believe what they want to believe!people believe what they want to believe!</span>
            <a href="#" class="delete" onclick="delLink(this)">delete</a>
            <div id="expire" class="expire">15分钟后过期</div>
        </div>
    </div>
<script type="text/javascript">
    (function () {
        var httpRequest;
        document.getElementById("submit").onclick = makePost;
        function makePost() {
            var pwd = document.getElementById("password").value;
            if (pwd === undefined || pwd.trim() === null || pwd.trim() === "") {
                var err = document.getElementById("err");
                if(err === undefined || err === null) {
                    err = document.createElement("div");
                    err.setAttribute("class", "error");
                    err.setAttribute("id", "err");
                    err.innerText = "请输入密码！";
                    document.getElementById("main").insertBefore(err, document.getElementById("verify"));
                }
                return;
            }
            var err = document.getElementById("err");
            if (err !== undefined && err !== null) {
                document.getElementById("err").style.display="none";
            }
            if (window.XMLHttpRequest) {
                httpRequest = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                try {
                    httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {}
                }
            }
            httpRequest.onreadystatechange = showMsg;


            var data = "&password="+ encodeURIComponent(pwd.trim());
            console.log(data);
            httpRequest.open("POST",window.location.href+data);
            httpRequest.send(null);
        }

        function showMsg() {
            var msg = JSON.parse(httpRequest.responseText);
            document.getElementById("verify").style.display="none";
            if (msg === undefined || msg === null || msg.txt === undefined) {
                var err = document.createElement("div");
                err.setAttribute("class","error");
                err.innerText = "密码错误或该信息已过期或被删除。";
                document.getElementById("main").appendChild(err);
            } else {
                document.getElementById("hid").value = msg.uuid;
                document.getElementById("txt").innerText = msg.txt;
                document.getElementById("expire").innerText = Math.round(msg.expire / 60) + "分钟后过期";
                document.getElementById("msg").style.display="block";
            }
        }
    })();

    function delLink(t) {
        var p = t.parentNode;
        var gp = p.parentNode;

        var uuid = document.getElementById("hid").value;

        var httpRequest;
        if (window.XMLHttpRequest) {
            httpRequest = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            try {
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }

        httpRequest.onreadystatechange = function () {
            gp.removeChild(p);
            var err = document.createElement("div");
            err.setAttribute("class","error");
            err.innerText = "该信息已删除。3秒后跳转到首页。";
            document.getElementById("main").appendChild(err);
            setTimeout("window.location.href='http://smessage.coding.io'",3000);
        }

        httpRequest.open("POST","delete?id="+encodeURIComponent(uuid));
        httpRequest.send(null);
    }

</script>
</body>
</html>