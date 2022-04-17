package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.managers.*;
import com.thinking.machines.network.common.exceptions.*;
import com.thinking.machines.network.client.*;

public class DesignationManager implements DesignationManagerInterface
{
private static DesignationManagerInterface designationManager=null;
private DesignationManager() 
{
}


public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null)
{
designationManager=new DesignationManager();
}
return designationManager;
}


public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=null;
try
{
blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","code should be zero");
}
if(title==null)
{
blException.addException("title","title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","length of title is zero");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient=new NetworkClient();
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException) response.getException();
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}catch(NetworkException ne)
{
blException=new BLException();
blException.setGenericException(ne.getMessage());
throw blException;
}
}

public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","invalid code : "+code);
throw blException;
}
if(title==null)
{
blException.addException("title","title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","length of title is zero");
}
}
if(blException.hasExceptions())
{
throw blException;
}

try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient=new NetworkClient();
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}

public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException(); 
if(code<=0)
{
blException.addException("code","invalid code : "+code);
throw blException;
}
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION));
request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}

DesignationInterface getDSDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}


public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=null;
if(code<=0)
{
blException=new BLException(); 
blException.addException("code","invalid code : "+code);
throw blException;
}
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE));
request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
return (DesignationInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
if(title==null)
{
blException.addException("title","title required");
throw blException;
}
title=title.trim();
if(title.length()==0)
{
blException.addException("title","length of title is zero");
throw blException;
}
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE));
request.setArguments(title);
NetworkClient networkClient=new NetworkClient();
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
return (DesignationInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}

public int getDesignationCount()
{
BLException blException=null;
Response response=null;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
NetworkClient networkClient=new NetworkClient();
response=networkClient.send(request);
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
return (Integer)response.getResult();
}

public boolean designationCodeExists(int code)
{
Response response=null;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));
request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
response=networkClient.send(request);
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
return (boolean)response.getResult();
}

public boolean designationTitleExists(String title)
{
Response response=null;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));
request.setArguments(title);
NetworkClient networkClient=new NetworkClient();
response=networkClient.send(request);
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
return (boolean)response.getResult();
}

public Set<DesignationInterface> getDesignations()
{
Response response=null;
BLException blException=null;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS));
NetworkClient networkClient=new NetworkClient();
response=networkClient.send(request);
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
Set<DesignationInterface> ss=(Set<DesignationInterface>)response.getResult();
return ss;
}

}