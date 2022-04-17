import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
class psp
{
public static void main(String gg[])
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String name=gg[0];
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(gg[1]);
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
char gender=gg[2].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[3]);
BigDecimal basicSalary=new BigDecimal(gg[4]);
String panNumber=gg[5];
String aadharCardNumber=gg[6];
employeeDTO.setName(name);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDAO.add(employeeDTO);

}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}