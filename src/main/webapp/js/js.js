    var ticketDone;
    var ticketNotDone;
    var checkDone;
    var tableDone;
    var notDoneTable;
    function send() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/todolist/listdone.do",
        dataType: "json"
    }).done(
        function(data) {
            ticketDone = data.ticketDone;
            ticketNotDone = data.ticketNotDone;
            checkDone = document.getElementsByName('notDoneCheck');
            notDoneTable = document.getElementsByName('notDoneTable');
            ticketNotDone.forEach(function (item, i, arr) {
                $('#notDoneTable tr:last').after('<tr><th scope="row">' + item.id + '</th><td>'
                    + item.name + '</th><td>'
                    + item.description + '</th><td>'
                    + item.created + '</th><td>'
                    + '<input class="form-check-input" '
                    + 'type="checkbox" id="' + item.id + '" '
                    + 'onchange="clickTicked(' + item.id + ')"'
                    + 'name="' + item.id + '">'
                    + '<label class="form-check-label" for="notDone">'
                    + 'Выполнено'
                    + '</label>'
                    + '</td><td>'
                    + '<a class="btn btn-danger" onclick="clickDeleteTicked(' + item.id + ')" aria-label="Удалить">'
                    + '<i class="fa fa-trash-o" aria-hidden="true"></i>'
                    + '</a>'
                    + '</td></tr>');
            });
            showDone();
        }
    ).fail( function(xhr, textStatus, errorThrown) {
        alert(JSON.stringify(xhr));
    });
}

    function clickCheckBox() {
    reloadWindow();
}

    function reloadWindow() {
    window.location.reload();
}

    function showDone() {
    if (document.getElementById('notDoneCheck').checked) {
    ticketDone.forEach(function (item, i, arr) {
    $('#doneTable tr:last').after('<tr><th scope="row">' + item.id + '</th><td>'
    + item.name + '</th><td>'
    + item.description + '</th><td>'
    + item.created + '</th><td>'
    + '<input class="form-check-input" checked="true" type="checkbox" id="' + item.id + '" '
    + 'name="' + item.id + '" '
    + 'onchange="clickTicked(' + item.id + ')">'
    + '<label class="form-check-label" for="notDone">'
    + 'Выполнено'
    + '</label>'
    + '</td><td>'
    + '<a class="btn btn-danger" onclick="clickDeleteTicked(' + item.id + ')" aria-label="Удалить">'
    + '<i class="fa fa-trash-o" aria-hidden="true"></i>'
    + '</a>'
    + '</td></tr>');
});
}
}

    $(document).ready( function(){
    send();
});

    function clickTicked(id) {
    $.ajax({
        url: 'http://localhost:8081/todolist/clickdone.do',
        method: 'post',
        dataType: 'html',
        data: {text: id},
        success: function(data){
            window.location.reload();
        }
    });
}

    function clickDeleteTicked(id) {
    $.ajax({
        url: 'http://localhost:8081/todolist/deleteticket.do',
        method: 'post',
        dataType: 'html',
        data: {text: id},
        success: function(data){
            window.location.reload();
        }
    });
}