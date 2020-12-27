$(function (){

    const tableBodyTimers = $('#tableBodyTimers');
    const inputName = $('#inputName');
    const buttonStart = $('#buttonStart');

    function updateTimers(jsonTimers) {
        let tableContent = "";
        $.each(jsonTimers, function(i, timer) {
            tableContent += "<tr>";
            tableContent += "<td>"+timer.name+"</td>";
            tableContent += "<td>"+timer.duration+"</td>";
            tableContent += "<td><button onClick='startTimer(\""+timer.name+"\")'>Start</button>";
            tableContent += "<button onClick='stopTimer(\""+timer.name+"\")'>Stop</button></td>";
            tableContent += "</tr>";
        });
        tableBodyTimers.html(tableContent);
    }

    $(document).ready(function() {
        refreshTable();
        setInterval(refreshTable, 1000);
    });

    function refreshTable() {
        $.ajax({
            type: 'GET',
            url: 'list',
            success: function (timers) {
                updateTimers(timers);
            }
        });
    }

    inputName.on("keyup", function(event) {
        if (event.key === 'Enter') {
            $.ajax({
                type: 'GET',
                url: 'start',
                data: {
                    name: inputName.val()
                }
            });
        }
    });

    buttonStart.on('click', function(){
        $.ajax({
            type: 'GET',
            url: 'start',
            data: {
                name: inputName.val()
            }
        });
    });

});

function stopTimer(name) {
    $.ajax({
        type: 'GET',
        url: 'stop',
        data: {
            name: name
        }
    });
}

function startTimer(name) {
    $.ajax({
        type: 'GET',
        url: 'start',
        data: {
            name: name
        }
    });
}