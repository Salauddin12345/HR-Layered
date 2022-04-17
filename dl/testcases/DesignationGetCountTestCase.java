import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationGetCountTestCase 
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
int s=designationDAO.getCount();
System.out.println("size:"+s);
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}