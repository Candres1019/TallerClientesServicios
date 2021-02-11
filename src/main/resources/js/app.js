
const app = (function () {

    function cargando(){
        console.log("JavaScript Cargado De Manera Satisfactoria");
    };

    function getDatos(){
        const info = $("#datos").val();
        const promise = $.get({
            url: "/Apps/hello",
            contentType: "application/json",
            data: JSON.stringify(info),
        });
        promise.then(function(data){
            console.log(data);
        }, function(error) {
            console.log("Failed");
        });
    }

    return {
        cargando: cargando,
        getDatos : getDatos
    }

})();