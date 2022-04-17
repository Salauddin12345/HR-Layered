package com.thinking.machines.hr.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.network.server.*;
import java.util.*;

public class RequestHandler implements RequestHandlerInterface 
{
public Response process(Request request)
{
DesignationManagerInterface designationManager; 
String managerName=request.getManager();
String action=request.getAction();
Object obj[]=request.getArguments();
Response response=new Response();
if(managerName.equals("DesignationManager"))
{
try
{
designationManager=DesignationManager.getDesignationManager();
if(action.equals("addDesignation"))
{
DesignationInterface d=(DesignationInterface)obj[0];
designationManager.addDesignation(d);
response.setSuccess(true);
response.setResult(d);
}
if(action.equals("updateDesignation"))
{
DesignationInterface d=(DesignationInterface)obj[0];
designationManager.updateDesignation(d);
response.setSuccess(true);
}
if(action.equals("removeDesignation"))
{
int code=(Integer)obj[0];
designationManager.removeDesignation(code);
response.setSuccess(true);
}
if(action.equals("getDesignationByCode"))
{
int code=(Integer)obj[0];
DesignationInterface d=(DesignationInterface)designationManager.getDesignationByCode(code);
response.setSuccess(true);
response.setResult(d);
}
if(action.equals("getDesignationByTitle"))
{
String title=(String)obj[0];
DesignationInterface d=(DesignationInterface)designationManager.getDesignationByTitle(title);
response.setSuccess(true);
response.setResult(d);
}
if(action.equals("getDesignationCount"))
{
int count=designationManager.getDesignationCount();
response.setSuccess(true);
response.setResult(count);
}
if(action.equals("designationCodeExists"))
{
int code=(Integer)obj[0];
boolean val=(boolean)designationManager.designationCodeExists(code);
response.setSuccess(true);
response.setResult(val);
}
if(action.equals("designationTitleExists"))
{
String title=(String)obj[0];
boolean val=(boolean)designationManager.designationTitleExists(title);
response.setSuccess(true);
response.setResult(val);
}
if(action.equals("getDesignations"))
{
response.setSuccess(true);
Set<DesignationInterface> set=designationManager.getDesignations();
response.setResult(set);
}
}catch(BLException blExce)
{
response.setException(blExce);
response.setSuccess(false);
}
}
else if(managerName.equals("EmployeeManager"))
{

}
else
{
BLException blException=new BLException();
blException.setGenericException("invalid manager name or action");
response.setException(blException);
response.setSuccess(false);
}
return response;
}

}