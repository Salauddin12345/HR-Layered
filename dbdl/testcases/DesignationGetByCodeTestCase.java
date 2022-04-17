import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;

public class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDTO=designationDAO.getByCode(code);
System.out.println("code:"+designationDTO.getCode()+" , title:"+designationDTO.getTitle());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}