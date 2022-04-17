import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
import com.thinking.machines.enums.*;

class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
Employee employee=new Employee();
employee.setName("bashir");
Designation designation=new Designation();
designation.setCode(5);
employee.setDesignation(designation);
String g="2/3/1311";
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employee.setDateOfBirth(sdf.parse(g));
}catch(ParseException parseException)
{
System.out.println("do nothing");
}
employee.setGender(GENDER.MALE);
employee.setIsIndian(true);
employee.setBasicSalary(new BigDecimal("200"));
employee.setPANNumber("pan12345");
employee.setAadharCardNumber("aadhar12345");
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("employee added with employee id : "+employee.getEmployeeId());

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