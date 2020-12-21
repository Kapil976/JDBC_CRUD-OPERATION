package com.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class JdbcTest {

	public static void main(String args[]) {
		Connection con = null;
		PreparedStatement stmt = null;
		Scanner scan = null;
		ResultSet rs = null;
		scan = new Scanner(System.in);
		while (true) {
			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.173:1521:ORA10G", "TRAINERP",
						"TRAINERP");
				System.out.println("Connection Establish");
				System.out.println("---------------------------------");
				System.out.println("CRUD OPERATION ");
				System.out.println(" Enter 1 Show all Record ");
				System.out.println(" Enter 2 Add New Record");
				System.out.println(" Enter 3 Edit Existing Record");
				System.out.println(" Enter 4 Delete Existing Record");
				System.out.println(" Enter 5 Exit ");
				System.out.println("---------------------------------");
				System.out.print("Enter Number Please:");
				int number = scan.nextInt();
				switch (number) {
				case 1:
					stmt = con.prepareStatement("select * from test_trainee");
					rs = stmt.executeQuery();
					System.out.println("---------------------All Records-------------------------");
					System.out.println("EMPID\tDEPTNO\tNAME\tSAL\tHIREDATE");
					while (rs.next()) {

						System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
								+ rs.getString(4) + "\t" + rs.getString(5));

					}
					break;
				case 2:

					String maxQuery = "(select max(empid+1) from test_trainee)";
					int maxCount = 0;
					try (PreparedStatement ps = con.prepareStatement(maxQuery); ResultSet rs1 = ps.executeQuery();) {
						if (rs1.next()) {
							maxCount = rs1.getInt(1);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					// System.out.println("maxCount--->"+maxCount);
					// Calendar calendar = Calendar.getInstance();
					// java.sql.Date dd = new java.sql.Date(calendar.getTime().getTime());
					System.out.println("Enter Department Number:");
					int departmentNumber = scan.nextInt();
					scan.nextLine();
					System.out.println("Enter Employee Name:");
					String name = scan.nextLine();
					System.out.println("Enter Salary :");
					int salary = Integer.parseInt(scan.nextLine());
					String insertTableSQL = "INSERT INTO test_trainee" + "(Empid,DEPTNO, NAME, SAL,HIREDATE) VALUES"
							+ "(?,?,?,?,sysdate)";
					// System.out.println("insertTableSQL---->" + insertTableSQL);
					stmt = con.prepareStatement(insertTableSQL);
					stmt.setInt(1, maxCount);
					stmt.setInt(2, departmentNumber);
					stmt.setString(3, name);
					stmt.setInt(4, salary);
					// stmt.setString(5, "sysdate");
					stmt.executeUpdate();
					System.out.println("New Record add successfully");
					break;
				case 3:

					System.out.print("Enter Employee ID:");
					int eid = scan.nextInt();

					String checkEmpid = "select empid from test_trainee where empid = '" + eid + "'";
					stmt = con.prepareStatement(checkEmpid);
					rs = stmt.executeQuery();
					boolean flag = false;
					if (rs.next()) {
						flag = true;
					}
					if (flag){

						String query = "Select * from test_trainee where empid='" + eid + "'";
						stmt = con.prepareStatement(query);
						rs = stmt.executeQuery();
						if (rs.next()) {
							System.out.println("Employee id:" + rs.getString(1));
							System.out.println("1.Department Name:" + rs.getInt(2));
							System.out.println("2.Employee Name:" + rs.getString(3));
							System.out.println("3.Employee Salary:" + rs.getInt(4));
							System.out.println("4.Employee Date:" + rs.getString(5));
						}

						System.out.print("Enter New Department Number:");
						int departmentNumberUpdate = scan.nextInt();
						System.out.print("Enter New Employee Name:");
						String nameUpdate = scan.next();
						scan.nextLine();
						System.out.print("Enter New Salary:");
						int salaryUpdate = scan.nextInt();
						query = "update test_trainee set  DEPTNO= ?, NAME = ?, SAL = ?  where empid='" + eid + "'";
						stmt = con.prepareStatement(query);
						System.out.println("Query--->" + query);
						stmt.setInt(1, departmentNumberUpdate);						
						stmt.setString(2, nameUpdate);
						stmt.setInt(3, salaryUpdate);
						stmt.executeUpdate();
						System.out.println("Record Updated....");
					} else {
						System.out.println("Employee Id Not Exits !");
					}

					break;
				case 4:
					System.out.print("Which Employee Id do you want delete please enter Employee ID:");
					String employeeId = scan.next();
					String deleteEmpid = "select empid from test_trainee where empid = '" + employeeId + "'";
					stmt = con.prepareStatement(deleteEmpid);
					rs = stmt.executeQuery();
					boolean flag1 = false;
					if (rs.next()) {
						flag1 = true;
					}
					if(flag1) {
						String query1 = "DELETE from test_trainee where empid='" + employeeId + "'";
						stmt = con.prepareStatement(query1);
						stmt.executeUpdate(query1);
						System.out.println("Employee id is " + employeeId + " delete successfully");
					}
					else {
						System.out.println("Employee Id Not Exits !");
					}

					break;
				case 5:
					System.out.println("Exit");
					System.exit(0);
					break;
				default:
					System.out.println("Could not Found Anything");
					break;
				}
				System.out
				.println("Do you want to use another CRUD Operation if you want press Y for Yes and N for NO:");
				String a = scan.next();
				String word = a.toUpperCase();
				if (word.equals("Y")) {
					continue;
				} // end of if
				else {
					System.out.println("Exit");
					scan.close();
					System.exit(0);
				}

			} catch (Exception e) {
				e.printStackTrace();

				break;
			} finally {
				try {
					con.close();
					// scan.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}