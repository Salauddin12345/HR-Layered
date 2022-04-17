import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;

public class DesignationDeleteTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("designation deleted");
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}