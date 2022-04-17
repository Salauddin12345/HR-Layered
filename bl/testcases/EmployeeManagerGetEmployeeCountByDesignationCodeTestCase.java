import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.math.*;
import java.util.*;
import java.text.*;
import com.thinking.machines.enums.*;

public class EmployeeManagerGetEmployeeCountByDesignationCodeTestCase
{
public static void main(String gg[])
{
try
{
int designationCode=1;
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
int x=employeeManager.getEmployeeCountByDesignationCode(designationCode);
System.out.println(x);
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