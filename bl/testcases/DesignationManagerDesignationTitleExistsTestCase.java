import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerDesignationTitleExistsTestCase
{
public static void main(String gg[])
{
try
{
String title=gg[0];
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.println("title "+title+" exists : "+designationManager.designationTitleExists(title));
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