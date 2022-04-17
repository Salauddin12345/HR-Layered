import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
class psp
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("designation added with code:"+designationDTO.getCode());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}