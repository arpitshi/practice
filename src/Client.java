import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public Client(){
        try{
            System.out.println("Sending request with the server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection is done");
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        }
        catch(IOException ex){
            ex.getStackTrace();
        }
    }
    public void startReading(){
        Runnable r1=()->{
            System.out.println("reader started");
            while(true){
                try{
                    String msg=in.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        break;
                    }
                    System.out.println("Server : "+msg);
                }
                catch(IOException ex){
                    ex.getStackTrace();
                }
            }

        };
        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2=()->{
            while(true){

                System.out.println("writer started");
                try{
                    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                    String content=br.readLine();
                    out.println(content);
                    out.flush();
                }
                catch(IOException ex){
                    ex.getStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        new Client();
    }
}
