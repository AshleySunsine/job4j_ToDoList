<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.todolist.Ticket" %>
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

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>ToDoList</title>
</head>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>

<body>
<form action = "send()" method="post">
<script>
    function send() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/todolist/listdone.do",
            dataType: "json"
        }).done(
            function(data) {
              var ticketDone = data.ticketDone;
              var ticketNotDone = data.ticketNotDone;

              ticketDone.forEach(function (item, i, arr) {
                  $('#doneTable tr:last').after('<tr><th scope="row">' + item.id + '</th><td>'
                      + item.name + '</th><td>'
                      + item.description + '</th><td>'
                      + item.created + '</th><td>'
                      + '<input class="form-check-input" checked="true" type="checkbox" id="' + item.id + '" name="' + item.id + '">'
                      + '<label class="form-check-label" for="notDone">'
                      + 'Выполнено'
                      + '</label>'
                      + '</td></tr>');
              });

                ticketNotDone.forEach(function (item, i, arr) {
                    $('#notDoneTable tr:last').after('<tr><th scope="row">' + item.id + '</th><td>'
                        + item.name + '</th><td>'
                        + item.description + '</th><td>'
                        + item.created + '</th><td>'
                        + '<input class="form-check-input" type="checkbox" id="' + item.id + '" name="' + item.id + '">'
                        + '<label class="form-check-label" for="notDone">'
                        + 'Выполнено'
                        + '</label>'
                        + '</td></tr>');
                });

        }
        ).fail( function(xhr, textStatus, errorThrown) {
            alert(JSON.stringify(xhr));
        });
    }

    function showDone(items) {

    }
</script>
    <form>
        <hr>
        <div class="col-md-4 mb-3">
            <label for="todo">Название задачи</label>
            <input type="email" class="form-control" name="todo" id="todo" placeholder="Название задачи">
        </div>

        <div class="col-md-5 mb-3">
            <label for="description">Описание задачи</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <hr>
        <div class="col-md-4 mb-3">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="notDone" name="notDone">
                <label class="form-check-label" for="notDone">
                    Показать выполненые задачи
                </label>
            </div>
        </div>

    </form>
    <div class="col-md-7">
        <div class="row">
            <div class="card" style="width: 100%" name="Post">
                <div class="card-header">
                    Задачи:
                </div>
                <div class="card-body">
                    <table class="table table-striped table-secondary" id="notDoneTable">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Название</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Дата создания</th>
                            <th scope="col">Статус</th>

                        </tr>
                        </thead>
                        <tbody>

                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-7">
        <div class="row">
            <div class="card" style="width: 100%" name="Post">
                <div class="card-header">
                    Выполненые задачи:
                </div>
                <div class="card-body">
                    <table class="table table-striped table-success" id="doneTable">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Название</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Дата создания</th>
                            <th scope="col">Статус</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <button type="button" class="btn btn-primary" onclick="send()">Кнопка</button>
</form>
</body>
</html>
