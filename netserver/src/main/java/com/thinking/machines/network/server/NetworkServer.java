package com.thinking.machines.network.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.net.*;
import java.io.*;
import java.util.*;


public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null) throw new NetworkException("Request Handler Missing");
this.requestHandler=requestHandler;
}

public void startListening()
{
try
{
int port=0;
ServerSocket serverSocket=null;
Socket socket=null;
RequestProcessor requestProcessor=null;
try
{
port=Configuration.getPort();
}catch(NetworkException networkException)
{
throw new NetworkException("invalid port");
}
try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException("unable to start server at port number :"+port);
}

while(true)
{
System.out.println("server is ready to accept request at port No. "+port);
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,requestHandler);
}

}catch(Exception e)
{
System.out.println(e);
}
}

}