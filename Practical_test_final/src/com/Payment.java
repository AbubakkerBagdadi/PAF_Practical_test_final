//Registration Number : IT18191820
//Name : S.A.Bakthathi
//Workload : Payment Management Service
//Group Name : S1141.4  



package com;

import java.sql.*;

public class Payment {
	
	//Connection
public Connection connect()
	{
		Connection con=null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newdb","root","sliit123"); 
						
			
		}
		catch(Exception e)
		{
			System.out.print("error");
			e.printStackTrace(); 
		}
		return con;
	}

	//Add Data
	public String addPayment(String amount,String nic,String cardnumber,String cardname,String expdate,String cvv)
	{
	String Output ="";
	
	try {
		Connection con = connect();

		if (con == null) {
		}

		String query = " insert into payment (`invoiceNumber`,`amount`,`nic`, `cardnumber`, `cardname`,`expdate`,`cvv`) "
				+ "values(?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		preparedStmt.setInt(1,0);
		preparedStmt.setString(2, amount);
		preparedStmt.setString(3, nic);
		preparedStmt.setInt(4, Integer.parseInt(cardnumber) );
		preparedStmt.setString(5,cardname );
		preparedStmt.setString(6,expdate );
		preparedStmt.setString(7,cvv );
		
		
		
		preparedStmt.execute();
		con.close();
		String newPay = readPayment();
		Output = "{\"status\":\"success\", \"data\": \"" + newPay + "\"}";
	}
	catch (Exception e)
	{
		Output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment.\"}";
		System.err.println(e.getMessage());
	}
	return Output;
	}
	
	//Delete
	public String deletePayment(String cardnumber)
	{
		String output="";
		
		try {
			Connection connection=connect();
			
			if(connection==null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			String query="delete from payment where cardnumber=?";
			
			PreparedStatement prepareStmt=connection.prepareStatement(query);
			
			prepareStmt.setInt(1, Integer.parseInt(cardnumber));
			prepareStmt.execute();
			connection.close();
			
			output= "deleted successfully";
		}
		catch(Exception e)
		{
			output="error while deleting the payment";
			System.err.println(e.getMessage());
		}
		return output;
			
	}
	

	//Update
	public String updatePayment(String nic,String cardnumber,String cardname,String expdate,String cvv,String invoiceNumber)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for updating."; }
			// create a prepared statement
			String query = "UPDATE payment SET   nic=?, cardnumber=?, cardname=?, expdate=?, cvv=? WHERE invoiceNumber=? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, nic);
			preparedStmt.setInt(2, Integer.parseInt(cardnumber) );
			preparedStmt.setString(3,cardname );
			preparedStmt.setString(4,expdate );
			preparedStmt.setString(5,cvv );
			preparedStmt.setString(6,invoiceNumber );
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	} 
	
	//Read data
		public String readPayment()
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{return "Error while connecting to the database for reading."; }
				
				// Prepare the html table to be displayed
				output = "<table class=\"table table-striped\"><tr><th>Amount</th><th>NIC</th>"+
						"<th>CardNumber</th><th>CardName</th><th>Exp_Date</th><th>CVV</th><th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from Payment";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next())
				{
					
					
					String invoiceNumber = Integer.toString(rs.getInt("invoiceNumber"));
					String amount = rs.getString("amount");
					String payment_nic = rs.getString("nic");
					String cardnumber = Integer.toString(rs.getInt("cardnumber"));
					String cardname = rs.getString("cardname");
					String expdate = rs.getString("expdate");
					String cvv = rs.getString("cvv");

					
					// Add into the html table
					output += "<tr><td><input id='hidpaymentIDUpdate' name='hidpaymentIDUpdate' type='hidden' value='"
							+ invoiceNumber + "'>" + amount + "</td>";
					
					output += "<td>" +payment_nic + "</td>";
					output += "<td>" + cardnumber + "</td>";
					output += "<td>" + cardname + "</td>";
					output += "<td>" + expdate + "</td>";
					output += "<td>" + cvv + "</td>";
					
					
					// buttons event performances
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paycard='"
							+ cardnumber + "'></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the Payment.";
				System.err.println(e.getMessage());
			}
			return output;
		}
	
}
