import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;

class psp
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
boolean count=designationManager.designationTitleExists("driver");
System.out.println(count);
}catch(Exception e)
{
System.out.println("in catch block");
System.out.println(e.getMessage());
}
}
}