package com.thinking.machines.network.client;
import com.thinking.machines.network.common.exceptions.*;

import org.xml.sax.*;
import javax.xml.xpath.*;
import java.io.*;

public class Configuration
{
private static String host;
private static int port;
private static boolean malformed;
private static boolean fileMissing;
static
{
try
{
File file=new File("server.xml");
if(file.exists()==true)
{
InputSource inputSource=new InputSource("server.xml");
XPath xPath=XPathFactory.newInstance().newXPath();
host=xPath.evaluate("//server/@host",inputSource);
try
{
port=Integer.parseInt(xPath.evaluate("//server/@port",inputSource));
}catch(NumberFormatException numberFormatException)
{
malformed=true;
}
}
else
{
fileMissing=true;
}
}catch(Exception e)
{
malformed=true;
}
}

public static String getHost() throws NetworkException
{
if(fileMissing) throw new NetworkException("server.xml is missing");
if(malformed) throw new NetworkException("server.xml not configured according to documenation");
if(host==null) throw new NetworkException("invalid host,server.xml not configured according to documenation");
return host;
}
public static int getPort() throws NetworkException
{
if(fileMissing) throw new NetworkException("server.xml is missing");
if(malformed) throw new NetworkException("server.xml not configured according to documenation");
if(port<=1024 || port>49151 ) throw new NetworkException("invalid port number,server.xml not configured according to documenation");
return port;
}
}