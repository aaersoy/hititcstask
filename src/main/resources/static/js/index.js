$( document ).ready(function() {

    $.ajax({
        type: "get",
        url: "/getallrentacarids",
        dataType: 'json',
        statusCode:{
            202: function (data){
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
                    $("#newCarFormBtn").css('color','red')
                    $("#newCarFormBtn").after("<span class='error' style='color: red ; font-size:10px'> Rent A Car Ekleyin.</span>");
                }
            },
            500:function (){
                alert("İşlem esnasında bir hata oluştu.")
            }
        }

    });

});

$('#newCarForm').submit(function (e) {
    e.preventDefault();
    var carData = {}
    carData["plateCode"] = $("#plateCodeText").val();
    carData["rentACarID"] = $('#rentACarList option:selected').val();
    var regex= /^[0-9]*$/;
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
                statusCode:{
                    202: function (){
                        window.location.href = "/";
                    },
                    500:function (){
                        alert("İşlem esnasında bir hata oluştu. Tekrarlayan eleman olabilir .")
                    }
                }});
        }
    });

$('#newRentACarForm').submit(function (e) {
    e.preventDefault();

    var rentACarData = {}
    rentACarData["rentACarName"] = $("#rentACarNameTxt").val();

    var regex= /[^\s]$/;
    var validRentACarName=regex.test(rentACarData["rentACarName"]);

    if(!validRentACarName){
        alert("Rent A Car adı formata uygun değil.")
    }
    else{

    $.ajax({
            type: "post",
            contentType: "application/json",
            url: "/newrentacar",
            data: JSON.stringify(rentACarData),
            dataType: 'json',
            statusCode:{
                202: function (){
                    window.location.href = "/";
                },
                500: function (){
                    alert("İşlem esnasında bir hata oluştu. Tekrarlayan eleman olabilir .")
                }
            }});}

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

