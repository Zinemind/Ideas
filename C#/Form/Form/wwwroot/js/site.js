$(document).ready(function () {
    $("#details").hide();
    $("#btn").click(function () {
        debugger;
        btnSubmit();
    });
});
// submit form
function btnSubmit() {
    debugger;
    var name = $("#name").val();
    var email = $("#email").val();
    var phone = $("#phone").val();
    var age = $("#age").val();
    if (name != "") {
        $.ajax({

            type: 'POST',
            url: 'Home/Register',
            data: { Name: name, Email: email, PhoneNumber: phone, Age: age, Id: 0 },
            cache: false,
            success: function (result) {
                debugger;
                alert(result);
            }
        });
    }
    
}