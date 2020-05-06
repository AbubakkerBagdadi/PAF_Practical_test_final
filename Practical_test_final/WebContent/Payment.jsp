<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
<div class="col-8">
<h1>Payment Management</h1>
			<form id="formPay" name="formPay" method="post">
	  
				    Amount: <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br>
						
				    NIC: <input	id="nic" name="nic" type="text" 
				    	class="form-control form-control-sm"> <br> 
				    	
				    Card Number: <input id="cardnumber" name="cardnumber" type="text"
						class="form-control form-control-sm"> <br>
						
				    Card Name:<input id="cardname" name="cardname" type="text"
						class="form-control form-control-sm"> <br> 
						
					Expire Date:<input id="expdate" name="expdate" type="date"
						class="form-control form-control-sm">  <br>
						
					CVV: <input	id="cvv" name="cvv" type="text"
						class="form-control form-control-sm"> <br>
	    
				<input id="btnSave" name="btnSave" type="button" value="Save"class="btn btn-primary"> 
		
				<input type="hidden"id="hidpaymentIDSave" name="hidpaymentIDSave" value="">
			</form>
			
			<!--  Alert messages for the events--><br>
			<div id="alertSuccess" class="alert alert-success"></div> 
			<div id="alertError" class="alert alert-danger">
	
			</div>
			<br>
			<div id="divPaymentGrid">
			      <%
			           Payment pay  = new Payment();
					  out.print( pay.readPayment());
			      %>
			</div>
 		</div>
 	</div>
 </div>
</body>
</html>