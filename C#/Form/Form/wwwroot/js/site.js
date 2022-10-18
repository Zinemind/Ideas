$(document).ready(function () {
    $("#details").hide();
    $("#btn").click(function () {
       // debugger;
        btnSubmit();
    });
});
// submit form
function btnSubmit() {
   // debugger;
    var name = $("#name").val();
    var email = ValidateEmail( $("#email").val());
    var phone = $("#phone").val();
    var age = ValidateNumber( $("#age").val());
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
// Email Validation
function ValidateEmail(inputText) {
    //debugger;
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (inputText.match(mailformat)) {
        return true;
    }
    else {
        alert("You have entered an invalid email address!");
        return false;
    }
}
// Number Validation
function ValidateNumber(number) {
    if (isNaN(number)) {
        alert('Please provide a valid  Number as Age');
    }
}
