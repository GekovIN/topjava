const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function updateFiltered() {
    var startDate = document.getElementById("startDate").value;
    var startTime = document.getElementById("startTime").value;
    var endDate = document.getElementById("endDate").value;
    var endTime = document.getElementById("endTime").value;

    $.get(ajaxUrl +
        "updateFiltered?startDate=" + startDate +
        "&startTime=" + startTime +
        "&endDate=" + endDate +
        "&endTime=" + endTime, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}