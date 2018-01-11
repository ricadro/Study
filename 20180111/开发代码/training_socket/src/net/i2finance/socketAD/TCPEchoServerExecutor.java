package net.i2finance.socketAD;

import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.concurrent.TimeUnit;  
import java.util.logging.Logger;  
  
  
  
public class TCPEchoServerExecutor {  
  
    public static void main(String[] args) throws IOException {  
        // Create a server socket to accept client connection requests  
        ServerSocket servSock = new ServerSocket(5500);  
  
        Logger logger = Logger.getLogger("practical");  
          
        // Run forever, accepting and spawning threads to service each connection  
        while (true) {  
            Socket clntSock = servSock.accept(); // Block waiting for connection  
            //executorService.submit(new EchoProtocol(clntSock, logger));  
            try {  
                ThreadPoolTaskExecutor.invoke(new EchoProtocol(clntSock, logger), TimeUnit.SECONDS, 3);  
            } catch (Exception e) {  
            }   
            //service.execute(new TimelimitEchoProtocol(clntSock, logger));  
        }  
        /* NOT REACHED */  
    }  
}  
