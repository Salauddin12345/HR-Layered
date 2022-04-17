import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
import com.thinking.machines.enums.*;

class EmployeeManagerGetEmployeesTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees=employeeManager.getEmployees();
for(EmployeeInterface employee:employees)
{
System.out.println();
System.out.println("employeeId : "+employee.getEmployeeId());
System.out.println("name : "+employee.getName());
System.out.println("designation Code : "+employee.getDesignation().getCode());
System.out.println("date Of Birth : "+employee.getDateOfBirth());
System.out.println("gender : "+employee.getGender());
System.out.println("is Indain : "+employee.getIsIndian());
System.out.println("basic Salary : "+employee.getBasicSalary());
System.out.println("PAN Number : "+employee.getPANNumber());
System.out.println("aadhar Card Number : "+employee.getAadharCardNumber());
}
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