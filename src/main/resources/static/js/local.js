
function sendData(path){
    var myForm = document.getElementById("myform")
    var formData = new FormData(myForm);
    var jsonData = {};
    //conversion de datos a json
    for(var[k,v] of formData){
        jsonData[k] = v;
    }
    const request = fetch(path, {
    method: "POST",
    headers: {
        'Accept': 'application/json',
        'content-type' : 'application/json'
    },
    body: JSON.stringify(jsonData)
    });
    console.log(request)
}