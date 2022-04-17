import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerGetDesignationsTestCase
{
public static void main(String gg[])
{

try
{
Set<DesignationInterface> set1=null;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
set1=designationManager.getDesignations();
set1.forEach((t)->{
System.out.println(t.getCode()+","+t.getTitle());
});
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