$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// identify the method
	var type = ($("#hidpaymentIDSave").val() == "") ? "POST" : "PUT";
	// sending data to the DC-Bus
	$.ajax({
		url : "paymentAPI",
		type : type,
		data : $("#formPay").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});

});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidpaymentIDSave").val("");
	$("#formPay")[0].reset();
}

// DELET implementation
$(document).on("click", ".btnRemove", function(event) {

	$.ajax({
		url : "paymentsAPI",
		type : "DELETE",
		data : "paycard=" + $(this).data("paycard"),
		dataType : "text",
		complete : function(response, status) {
			onpaymentDeleteComplete(response.responseText, status);
		}
	});

});

function onpaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document)
		.on(
				"click",
				".btnUpdate",
				function(event) {
					$("#hidpaymentIDSave").val(
							$(this).closest("tr").find('#hidpaymentIDUpdate').val());
					$("#amount").val(
							$(this).closest("tr").find('td:eq(0)').text());
					$("#nic").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#cardnumber").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#cardname").val(
							$(this).closest("tr").find('td:eq(3)').text());
					$("#expdate").val(
							$(this).closest("tr").find('td:eq(4)').text());
					$("#cvv").val(
							$(this).closest("tr").find('td:eq(5)').text());
					

				});

// CLIENTMODEL================================For the
// validation=========================================
function validatePaymentForm() {
	
	if ($("#amount").val().trim() == "") {
		return "Insert Amount.";
	}
	
	if ($("#nic").val().trim() == "") {
		return "Insert Nic Number";
	}
	
	if ($("#cardnumber").val().trim() == "") {
		return "Insert Card Number.";
	}
	
	if ($("#cardname").val().trim() == "") {
		return "Insert Card Name.";
	}
	
	if ($("#expdate").val().trim() == "") {
		return "Insert Card Expire Date.";
	}
	
	if ($("#cvv").val().trim() == "") {
		return "Insert CVV Number";
	}
	var tmpPrice = $("#amount").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Payment amount.";
	}
	// convert to decimal price
	$("#amount").val(parseFloat(tmpPrice).toFixed(2));

	// check the card number is empty and its number
	if ($("#cardnumber").val().trim() == "") {
		return "Insert Item Card Number.";
	}

	var tempCardno = $("#cardnumber").val().trim();
	if (!$.isNumeric(tempCardno)) {
		return "Insert a numerical value for Card Number.";
	}

	// check the postal number is empty and its number
	if ($("#cvv").val().trim() == "") {
		return "Insert Item CVV Number.";
	}

	var tempPostalno = $("#cvv").val().trim();
	if (!$.isNumeric(tempPostalno)) {
		return "Insert a numerical value for CVV Number.";
	}

	return true;
}
