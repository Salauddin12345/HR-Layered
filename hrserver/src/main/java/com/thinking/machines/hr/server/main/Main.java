package com.thinking.machines.hr.server.main;

import com.thinking.machines.hr.server.*;
import com.thinking.machines.network.server.*;
import com.thinking.machines.network.common.exceptions.*;

public class  Main
{
public static void main(String gg[])
{
try
{
RequestHandler requestHandler=new RequestHandler();
NetworkServer networkServer=new NetworkServer(requestHandler);
networkServer.startListening();
}catch(NetworkException e)
{
System.out.println(e.getMessage());
}
}
}