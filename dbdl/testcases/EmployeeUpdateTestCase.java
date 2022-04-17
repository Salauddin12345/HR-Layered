import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
try
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=simpleDateFormat.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
GENDER gender;
String tmp=gg[4];
if(tmp.charAt(0)=='m' || tmp.charAt(0)=='M') gender=GENDER.MALE;
else gender=GENDER.FEMALE;

boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAO employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("mark1");
employeeDAO.update(employeeDTO);
System.out.println("data updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}