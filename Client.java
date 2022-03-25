import java.net.*;

import java.io.*;

public class Client {
    Socket s;
    BufferedReader input; 
    DataOutputStream output;
    String recievedMessage = "";

    public static void main(String[] args) {
    
    }

    public Client(String IP, int port){
        try {
            s = new Socket(IP, port);
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            output = new DataOutputStream( s.getOutputStream());

        } catch (Exception e) {
            
        }
    }

    public void handshake(){
        send("HELO");
        receive();

        send("OK");
        receive();

        String username = System.getProperty("user.name");
        send("AUTH " + username);
        receive();

        send("REDY");
        receive();
 
        //Job scheduling
        terminate();
    }


    public void send(String message) {
        try {
            output.write(message.getBytes());
            output.flush();
        } catch (Exception e) {
            
        }

    }

    public void receive(){
        try {
            recievedMessage = input.readLine();
            System.out.println("Client recieved: " + recievedMessage);
        } catch (Exception e) {
            
        }

    }

    public void terminate(){
        send("QUIT");
        receive();
        if(recievedMessage.equals("QUIT")){

            try {
                input.close();
                output.close();
                s.close();
            } catch (Exception e) {
                
            }
            
        }
    }
}