package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;

public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManagerInterface designationManager=null;
private DesignationManager()  throws BLException
{
populateDataStructure();
}

private void populateDataStructure() throws BLException
{
codeWiseDesignationsMap=new HashMap<>();
titleWiseDesignationsMap=new HashMap<>();
designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface designationDTO:dlDesignations)
{
designation=new Designation();
designation.setCode(designationDTO.getCode());
designation.setTitle(designationDTO.getTitle());
this.codeWiseDesignationsMap.put(designation.getCode(),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
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
BLException blException;
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
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","designation "+title+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}

try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);

DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation;
dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title,dsDesignation);
designationsSet.add(dsDesignation);

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
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
if(code>0)
{
if(codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","invalid code : "+code);
throw blException;
}
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
if(title.length()>0)
{
DesignationInterface d=titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","designation "+title+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}

try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
new DesignationDAO().update(designationDTO);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}


}
public void removeDesignation(int code) throws BLException
{
BLException blException;
blException=new BLException();
if(code<=0)
{
blException.addException("code","invalid code : "+code);
throw blException;
}
if(code>0)
{
if(codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","invalid code : "+code);
throw blException;
}
}

try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
new DesignationDAO().delete(code);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}


}

DesignationInterface getDSDesignationByCode(int code) throws BLException
{
DesignationInterface designation;
designation=codeWiseDesignationsMap.get(code);
return designation;
}


public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation;
designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException;
blException=new BLException();
blException.addException("code","invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation;
designation=titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException;
blException=new BLException();
blException.addException("title","invalid title : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
public int getDesignationCount()
{
return designationsSet.size();
}
public boolean designationCodeExists(int code)
{
return codeWiseDesignationsMap.containsKey(code); 
}
public boolean designationTitleExists(String title)
{
return titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations;
designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}

}