import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;

public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.println(title+" exist:" +designationDAO.titleExists(title));
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}