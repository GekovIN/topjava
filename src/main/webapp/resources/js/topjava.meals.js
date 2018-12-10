const mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc":""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data) {
                        let s = new Date(data).toLocaleString().replace(",", "");
                        return s.substr(0, s.lastIndexOf(":"));
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                data.excess === false ?
                    $(row).attr("data-mealExcess", false) :
                    $(row).attr("data-mealExcess", true);
            }
            
        }),
        updateTable: updateFilteredTable
    });

    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i"
    });

    $("#startDate, #endDate").datetimepicker({
        timepicker:false,
        format: "Y-m-d"
    });

    $("#startTime, #endTime").datetimepicker({
        datepicker:false,
        format: "H:i"
    })
});

function saveMeal() {
    let cal = document.getElementById("calories").value;
    if (cal === "") {
        document.getElementById("calories").value = null;
    }

    // let date = document.getElementById("dateTime").value;
    // document.getElementById("dateTime").value = date.replace(" ", "T");

    save();
}