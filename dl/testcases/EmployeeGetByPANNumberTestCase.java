import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByPANNumberTestCase
{
public static void main(String gg[])
{
String panNumber=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employeeDTO;
employeeDTO=employeeDAO.getByPANNumber(panNumber);
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");

System.out.println("employee id : "+employeeDTO.getEmployeeId());
System.out.println("name : "+employeeDTO.getName());
System.out.println("designation Code : "+employeeDTO.getDesignationCode());
System.out.println("date of birth : "+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("gender : "+employeeDTO.getGender());
System.out.println("is indian : "+employeeDTO.getIsIndian());
System.out.println("basic salary : "+employeeDTO.getBasicSalary());
System.out.println("pan number : "+employeeDTO.getPANNumber());
System.out.println("aadhar card number : "+employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}