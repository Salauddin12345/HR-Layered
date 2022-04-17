import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerGetDesignationByCodeTestCase
{
public static void main(String gg[])
{

try
{
int code=Integer.parseInt(gg[0]);
DesignationInterface designation;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designation=designationManager.getDesignationByCode(code);
System.out.println(designation.getCode()+","+designation.getTitle());
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