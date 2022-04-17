import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{

try
{
DesignationInterface designation;
designation=new Designation();
designation.setCode(0);
designation.setTitle("manager");

DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("data updated");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}


}



}
}