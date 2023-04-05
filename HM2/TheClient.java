/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yasin
 * @author Matthew Orgeron, I am using the original file from 
 * the class example as my base for this assignment
 */
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
//As some point, we need to make this implement runnable,
//since this class must also be multithreaded.
public class TheClient{

    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream server_output = null;
    private DataOutputStream out = null;
    private Object lock = new Object();
    private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);//Will be used for while condition for thread2. 
    //make a thread that checks for input
    //make another thread that is ready to send output at anytime

    public MyClient(String address, int port){

        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }

        Thread thread1 = new Thread(){//This should be the output thread to the server.
            @Override
            public void run(){
                try{
                    input = new DataInputStream(System.in);
                    synchronized(lock){//both threads acces the socket directly here, so we need a synchronized block
                        out = new DataOutputStream(socket.getOutputStream());
                    }
                    String line = "";
                    while(!line.equals("Bye")){
                        line = input.readLine();//Get user input
                        out.writeUTF(line);//Write that input to the server
                    }
                    queue.put("Poison Pill");//Notify the other thread that they can stop listening in to the server with this.
                    input.close();
                    out.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        Thread thread2 = new Thread(){//This should be the input thread that reads from the server.
            @Override
            public void run(){
                try{
                    synchronized(lock){
                        server_output = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    }
                    while(queue.poll() == null){//use queue.poll instead
                        String str = server_output.readUTF();
                        System.out.print(str);//The client should not be given its own text, only the text from the other users
                    }
                    server_output.close();
                }catch(Exception e){
                    //e.printStackTrace();
                    ;
                }
            }
        };
        thread1.start();
        thread2.start();
        try{
            thread1.join();
            thread2.join();
            socket.close();//Close the socke AFTER the two threads finish running.
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

        MyClient client = new MyClient("localhost", Integer.parseInt(args[0]));//Port number is given by a command line argument
	}
    
    
}
