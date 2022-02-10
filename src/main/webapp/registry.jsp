<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.todolist.Store" %>
<%@ page import="ru.job4j.todolist.model.Ticket" %>
<%@ page import="ru.job4j.todolist.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script>
        function validate() {
            var name =  document.getElementById('name').value;
            var email = document.getElementById('email').value;
            var password = document.getElementById('password').value;
            if ((email == '') || (password == '') || (name == '')) {
                alert('Заполните поля');
            } else {
            let map = new Map();
            map.set("name", name);
            map.set("email", email);
            map.set("password", password);
            return map;
        }
        return -1;
        }

        function sendToServlet() {
            let params = validate();
            if ((params != null) && (params != -1)) {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8081/todolist/registry.do",
                    data: { name: params.get("name"), email: params.get("email"), password: params.get("password") }
                }).done(function( msg ) {
                    alert( "Data Saved: " + msg );
                });
            }
        }
    </script>
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Регистрация
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/registry.do" method="get">
                    <div class="form-group">
                        <label>Имя</label>
                        <input required type="text" class="form-control" name="name" id="name">
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input required type="text" class="form-control" name="email" id="email">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input required type="password" class="form-control" name="password" id="password">
                    </div>
                    <c:if test="${not empty error}">
                        <div style="color:red; font-weight: bold; margin: 30px 0;">
                            <c:out value="${error}"/>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary" onclick="sendToServlet()">Зарегистрировать</button>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>