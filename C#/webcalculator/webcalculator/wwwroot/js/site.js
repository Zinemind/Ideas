var firstValue = 0;
var secondValue = 0;
var operationType = "";
var clearRequired = true;
$(document).ready(function () {
    if (clearRequired) {
        $("#outValue").val("0");
    }
    $(".calcBtnD").click(function (e) {
        if (clearRequired) {
            firstValue = $("#outValue").val();
            $("#outValue").val("");
            clearRequired = false;
        }
        $("#outValue").val($("#outValue").val() + e.target.value);
    });

    //Operation onclick 
    $(".calcBtnO").click(function (e) {
        if (e.target.value != 'Equals') {
            operationType = e.target.value;
            clearRequired = true;
        }
    });


    //On submit equals
    $(".calcBtn").click(function (e) {
       
        //debugger;
        let IsNext = 1;
        let IsNumber =1;
        var preValue = $("#value").val(); // set the result to new variable
        if (preValue != 0) {  // check if a new entry
            if (!isNaN(e.target.value)) { // check if the value is  not a number
                if (!isNaN(parseInt(e.target.value))) {  // check if the value is a number
                    if ($("#CheckNumber").val() != 1) { 
                        IsNext = 0;
                    }
                   
                    
                }
                else {
                    IsNext = 1;
                }
            }
            else {
                $("#CheckNumber").val(IsNumber); // set CheckNumber to 1  if operation(+,-,/,* etc) done
                IsNext == 1;
            }
            if (IsNext == 0) {
                if (preValue != 0) { // if the previous result has value and a new entry comes, clear the previous value
                    firstValue = e.target.value;
                    $("#value").val("0");
                    $("#outValue").val(firstValue); // set the new value as first number
                }
            }
            
          
        }
        let targetValue = e.target.value;
        if (targetValue === 'Percentage') {
            operationType = 'Percentage';
            firstValue = $("#outValue").val();
        }
        if (targetValue === 'Equals' || targetValue === 'Percentage') {
            secondValue = $("#outValue").val();
            $.ajax({
                type: 'POST',
                url: 'Home/Calculation',
                data: { NumberA: firstValue, NumberB: secondValue, OperationType: operationType },
                cache: false,
                success: function (result) {
                    $("#outValue").val(result);
                    $("#value").val(result);
                    $("#a").val("0");
                }
            });
        }
        else if (targetValue === 'PN') {
            let calVal = parseFloat($("#outValue").val());
                $("#outValue").val(-1 * calVal);
        }
        else if (targetValue === 'AC'){
            $("#outValue").val("0");
            firstValue = 0;
            secondValue = 0;
        }
    });
});