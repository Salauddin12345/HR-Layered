package com.thinking.machines.network.server;
import com.thinking.machines.network.common.*;
import java.net.*;
import java.io.*;
import java.util.*;


public class RequestProcessor extends Thread
{
private Socket socket;
private RequestHandlerInterface requestHandler;
public RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}

public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesToReceive=1024;
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int i,j,k;
j=0;
i=0;
int bytesReadCount;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
int requestLength=0;
i=1;
j=1023;
while(j>=0)
{
requestLength=requestLength+(header[j]*i);
j--;
i=i*10;
}
bytesToReceive=requestLength;
byte request[]=new byte[requestLength];
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
request[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
System.out.println("Request recieved");
ByteArrayInputStream bais=new ByteArrayInputStream(request);
ObjectInputStream ois=new ObjectInputStream(bais);
Request req=(Request) ois.readObject();
Response res=requestHandler.process(req); // call to process method
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(res);
oos.flush();
byte objectBytes[]=baos.toByteArray();
int responseLength=objectBytes.length;
header=new byte[1024];
int x=responseLength;
j=1023;
while(x>0)
{
header[j]=(byte)(x%10);
x=x/10;
j--;
}
os.write(header,0,1024);
os.flush();
int bytesToSend=responseLength;
int chunkSize=1024;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
System.out.println("resposne sent");
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
