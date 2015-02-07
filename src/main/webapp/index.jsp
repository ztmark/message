<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link type="text/css" href="resource/css/main.css" rel="stylesheet" />

</head>
<body>
<header>
    创建一条几分钟后失效的信息
</header>
<div id="main">
    <%--<form action="" method="post">--%>
        <textarea id="text"  name="text" placeholder="输入内容..." ></textarea>
        <input id="radio1" type="radio" name="duration" value="15" checked/>
        <label for="radio1" class="duration">15分钟</label>
        <input id="radio2" type="radio" name="duration" value="60"/>
        <label for="radio2" class="duration">60分钟</label>
        <input id="radio3" type="radio" name="duration" value="1440"/>
        <label for="radio3" class="duration">1天</label>
        <input id="submit" class="create" type="submit" value="Create" />
    <%--</form>--%>

    <h3>Links</h3>
    <div id="link">
            <%--<c:if test="${msg != null}">
            <div><a href="${msg.uuid}">${pageContext.request.contextPath}/${msg.uuid}</a><span class="pwd">密码：${msg.password}</span> <a href="#" class="delete" onclick="delLink(this)">delete</a>
                <div class="expire">15分钟后过期</div> </div>
            </c:if>--%>
    </div>

</div>
<script type="text/javascript">
    (function (){
        var httpRequest;
        document.getElementById("submit").onclick = makePost;
        function makePost() {
            if (window.XMLHttpRequest) {
                httpRequest = new XMLHttpRequest();
            } else if (window.ActiveXObejct) {
                try {
                    httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {}
                }
            }
            httpRequest.onreadystatechange = addLink;

            var text = document.getElementById("text").value;
            var duration;
            var radios = document.getElementsByName("duration");
            for (var i=0; i<radios.length; i++) {
                if (radios[i].checked) {
                    duration = radios[i].value;
                    break;
                }
            }
            var data = "text="+ encodeURIComponent(text.trim())+"&duration="+duration;
            console.log(data);
            httpRequest.open("POST","add?"+data);
            httpRequest.send(null);
        }

        function addLink() {
            if (httpRequest.readyState === 4) {
                if (httpRequest.status === 200) {
                    var msg = JSON.parse(httpRequest.responseText);
                    var l = document.createElement("div");
                    var al = document.createElement("a");
                    var sp = document.createElement("span");
                    var ad = document.createElement("a");
                    var dx = document.createElement("div");
                    al.setAttribute("href",msg.uuid);
                    al.innerText = "${pageContext.request.contextPath}/" + msg.uuid;
                    sp.setAttribute("class","pwd");
                    sp.innerText = "密码：" + msg.password;
                    ad.setAttribute("class","delete");
                    ad.setAttribute("onclick","delLink(this)");
                    ad.innerText = "delete";
                    dx.setAttribute("class","expire");
                    var expire = Math.round(msg.expire) / 60;
                    dx.innerText = expire + "分钟后过期"
                    l.appendChild(al);
                    l.appendChild(sp);
                    l.appendChild(ad);
                    l.appendChild(dx);
                    document.getElementById("link").appendChild(l);
                    document.getElementById("text").value = "";
                } else {

                }
            }

        }



    })();

    function delLink(t) {
        var p = t.parentNode;
        var gp = p.parentNode;

        var uuid = p.firstChild.href;

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
        }

        httpRequest.open("POST","delete?uuid="+encodeURIComponent(uuid));
        httpRequest.send(null);
    }

</script>
</body>
</html>