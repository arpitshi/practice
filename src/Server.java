import java.io.*;
import java.net.*;
class Server{
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public Server(){
        try {
            serverSocket =new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting....");
            socket=serverSocket.accept();
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        } catch (IOException e) {
            // TODO: handle exception
            e.getStackTrace();
        }


    }
    public void startReading(){
        Runnable r1=()->{
            System.out.println("reader started");
            while(true){
                try{
                    String msg=in.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client terminated the chat");
                        break;
                    }
                    System.out.println("Client : "+msg);
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
        System.out.println("this is sever going to start the server");
        new Server();
    }
}