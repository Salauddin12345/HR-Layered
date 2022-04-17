import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
import com.thinking.machines.enums.*;

class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
try
{
String employeeId="A10000004";
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.println("employee removed");

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