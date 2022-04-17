import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeGetAllTestCase
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getAll();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
employees.forEach((employee)->{
System.out.println("employee id : "+employee.getEmployeeId());
System.out.println("name : "+employee.getName());
System.out.println("designation Code : "+employee.getDesignationCode());
System.out.println("date of birth : "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("gender : "+employee.getGender());
System.out.println("is indian : "+employee.getIsIndian());
System.out.println("basic salary : "+employee.getBasicSalary());
System.out.println("pan number : "+employee.getPANNumber());
System.out.println("aadhar card number : "+employee.getAadharCardNumber());
System.out.println();
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}