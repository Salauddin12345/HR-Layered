import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
import com.thinking.machines.enums.*;

class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
Employee employee=new Employee();
employee.setEmployeeId("a10000001");
employee.setName("riya");
Designation designation=new Designation();
designation.setCode(6);
designation.setTitle("electrician");
employee.setDesignation(designation);
String g="3/09/2000";
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employee.setDateOfBirth(sdf.parse(g));
}catch(ParseException parseException)
{
System.out.println("do nothing");
}
employee.setGender(GENDER.FEMALE);
employee.setIsIndian(false);
employee.setBasicSalary(new BigDecimal("5000000"));
employee.setPANNumber("pan1234");
employee.setAadharCardNumber("aadhar1234");
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("employee updated");

}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
properties.forEach((p)->{
System.out.println(blException.getException(p));
});
}

}
}