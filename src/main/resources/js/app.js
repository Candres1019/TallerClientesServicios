
const app = (function () {

    function cargando(){
        console.log("JavaScript Cargado De Manera Satisfactoria");
    }

    function sendDatos(){
        const promise = $.get({
            url: "/Apps/hello",
            contentType: "application/json",
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