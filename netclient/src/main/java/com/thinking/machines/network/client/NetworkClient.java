package com.thinking.machines.network.client;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.io.*;
import java.net.*;

public class NetworkClient 
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
byte []requestBytes=baos.toByteArray();
int bytesToSend=requestBytes.length;
byte []header=new byte[1024];
int x,i,j;
x=bytesToSend;
i=1023;
while(x>0)
{
j=x%10;
x=x/10;
header[i]=(byte)j;
i--;
}
Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);
os.flush();
int chunkSize=1024;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(requestBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
byte []tmp=new byte[1024];
header=new byte[1024];
int bytesToRecieve=1024;
int bytesReadCount=0;
InputStream is=socket.getInputStream();
j=0;
x=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++)
{
header[x]=tmp[i];
x++;
}
j=j+bytesReadCount; 
}
i=1023;
j=1;
bytesToRecieve=0;
while(i>=0)
{
bytesToRecieve+=(header[i]*j);
j=j*10;
i--;
}
byte []responseBytes=new byte[bytesToRecieve];
j=0;
x=0;
bytesReadCount=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++)
{
responseBytes[x]=tmp[i];
x++;
}
j=j+bytesReadCount;
}
ByteArrayInputStream bais=new ByteArrayInputStream(responseBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Response response=(Response)ois.readObject();
return response;
}catch(Exception e)
{
throw new NetworkException(e.getMessage());
}
}


}