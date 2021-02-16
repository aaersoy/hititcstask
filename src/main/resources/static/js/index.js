$( document ).ready(function() {

    $.ajax({
        type: "get",
        url: "/getallrentacarids",
        dataType: 'json',
        success: function (data) {
            var rentACarList=data["rentACarList"];
            var index;
            for(index=0; index<rentACarList.length; index++){
                $('#rentACarList').append(
                    "<option value='"+rentACarList[index]+"'>"+rentACarList[index]+"</option>"
                )
            }
            $(".error").remove();
            if(rentACarList.length==0){
                $("#newCarFormBtn").prop("disabled",true);
                $("#newCarFormBtn").after("<span class='error'> Rent A Car Ekleyin.</span>");

            }
        },
        error: function (e) {

            console.log(e);
        }
    });

});


$('#newCarForm').submit(function (e) {
    e.preventDefault();
    var carData = {}
    carData["plateCode"] = $("#plateCodeText").val();
    carData["rentACarID"] = $('#rentACarList option:selected').val();
    var regex= /^(0[1–9]|[1–7][0–9]|8[01])(([A-Z])(\d{4,5})|([A-Z]{2})(\d{3,4})|([A-Z]{3})(\d{2,3}))$/;
    var validPlateCode=regex.test(carData["plateCode"]);

    if(!validPlateCode){
        alert("Plaka kodu formata uygun değil.")
    }
    else {
            $.ajax({
                type: "post",
                contentType: "application/json",
                url: "/newcar",
                data: JSON.stringify(carData),
                dataType: 'json',
                success: function (data) {
                    // var carID=data["carID"];
                    // var appendRow = "<tr id='"+carID+"'>" +
                    //     "<td>" + data["carID"] + "</td>" +
                    //     "<td>" + data["plateCode"] + "</td>" +
                    //     "<td>" + data["rentACarID"] + "</td>" +
                    //     "<td>" + data["rented"] + "</td>" +
                    //     "<td><button class='btn-info' >Güncelle</button></td>" +
                    //     "<td><button class='btn-danger'  onclick='removeCar("+carID+")'>Sil</button></td>" +
                    //     "<td><button class='btn-default'  onclick='rentCar("+carID+")'>Araç Kirala</button></td>" +
                    //     "</tr>";
                    //
                    // $('#carTable tr:last').after(
                    //     appendRow
                    // );

                    window.location.href = "/";


                },
                error: function (e) {

                    console.log(e);


                }
            });
        }
    });

$('#newRentACar').submit(function (e) {
    e.preventDefault();

    var rentACarData = {}
    rentACarData["rentACarName"] = $("#rentACarName").val();
    var regex= /[a-zA-Z]+/;
    var validRentACarName=regex.test(rentACarData["rentACarName"]);

    if(!validRentACarName){
        alert("Rent A Car adı sadece karakterlerden oluşabilir.")
    }

    else{
        $.ajax({
            type: "post",
            contentType: "application/json",
            url: "/newrentacar",
            data: JSON.stringify(rentACarData),
            dataType: 'json',
            success: function (data) {
                // var appendRow = "<tr>" +
                //     "<td>" + data["rentACarID"] + "</td>" +
                //     "<td>" + data["rentACarName"] + "</td>" +
                //     "<td>" + data["carCount"] + "</td>" +
                //     "<td><button class='btn-info' >Güncelle</button></td>" +
                //     "<td><button class='btn-danger'>Sil</button></td>" +
                //     // "<td><button class='btn-danger' id=''>Sil</button></td>" +
                //     "<td><button class='btn-group'>Araç Bırak</button></td>" +
                //     "</tr>";
                //
                // $('#rentACarTable tr:last').after(
                //     appendRow
                // );
                window.location.href = "/";

            },
            error: function (e) {

                console.log(e);
                // var json = "Ajax Hata"
                //     + e.responseText;
                // $('#feedback').html(json);


            }
        });
    }
})

$('#carKeyWordForm').submit(function (e) {
    e.preventDefault();
    window.location.href="/searchcar?key="+$('#carKeyWordText').val();
})

$('#rentACarKeyWordForm').submit(function (e) {
    e.preventDefault();
    window.location.href="/searchrentacar?key="+$('#rentACarKeyWordText').val();
})

function removeCar(id){
    window.location.href="/removecar?id="+id;
}
function removeRentACar(id){
    window.location.href="/removerentacar?id="+id;
}
function rentCar(id){

    window.location.href="/rentcar?id="+id;
}
function releaseCar(id){
    window.location.href="/releasecar?id="+id;
}

function updateCar(id){
    window.location.href="/updatecar?id="+id;
}

function updateRentACar(id){
    window.location.href="/updaterentacar?id="+id;
}

