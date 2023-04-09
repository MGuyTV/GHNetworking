/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yasin
 * @author Matthew Orgeron, I am using the original program demoed
 * in class as a base for this class, and the same goes for the client
 * class as well
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TheServer {
    private ArrayList<String> client_names = new ArrayList<String>();//max 5 users?
    private ArrayList<Socket> clients = new ArrayList<Socket>();
    private Socket socket = null;
    private ServerSocket server = null;
    private Thread thread = null;
    private Object gameLock = new Object();//only 1 player should be able to access a player object at any given time

    private static int synchronized_counter = -1;
    private static Object lock = new Object();
    private static Object lock2 = new Object();
    private boolean bool = false;
    private static Game game;
    private Player myPlayer;//This player's player object
    private static ArrayBlockingQueue<Player> playerQueue = new ArrayBlockingQueue<Player>(2);
    public TheServer(int port){
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting client");
            /*****************************/

            while(!server.isClosed()){
                /*if(checkState()){//for now just let run forever and focus on getting the client output from the server.
                    System.out.println("Server Closed");
                    server.close();//
                }*/
                
                socket = server.accept();
                TheClientHandler handler = new TheClientHandler(socket);
                Thread thread = new Thread(handler);
                thread.start();//The problem is right here, it needs to be .start, not .run
                    
            }

        } catch (Exception e) {
            System.out.println("Error here " + e.getMessage());
        }

    }

    public void addPlayertoQueue(Player player){
        playerQueue.put(player);
    }
 

    public void writeToOtherClients(Socket mySocket, String string){//This method should output the message a client wrote
        try{                                                       //to all other clients
            synchronized(lock){
                if(clients.size() < 2)
                    return;// if this is the case, then there are no other clients currently connected, so just return.
                for(int i = 0; i < clients.size(); i++){
                    if(clients.get(i) == mySocket){//If we are looking at the current socket that's in the list, skip it since we don't want to 
                        continue;//print to the client their own message.
                    }else{//Else print this message to another client.
                        DataOutputStream out = new DataOutputStream(clients.get(i).getOutputStream());
                        out.writeUTF(string);
                    }
                }
            }

        }catch(Exception e){
            //e.printStackTrace();
            ;//This will trigger if you try to write to a client that has already left, so I'm just leaving it blank for now.
            //I tried to minimize this at the end of my run method, but it still doesn't totally prevent it, so I'm just leaving
            //this exception block empty.
        }
    }

    public void displayActiveUsers(Socket clientSocket){//Write all active users to all clients
        try{
            synchronized(lock2){
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF("A list of the current clients on this server:\n");
                writeToOtherClients(clientSocket, "A list of the current clients on this server:\n");
                for(int i = 0; i < client_names.size(); i++){
                    out.writeUTF("1. " + client_names.get(i) + "\n");
                    writeToOtherClients(clientSocket, "1. " + client_names.get(i) + "\n");
                }
                out.writeUTF("\n");
                writeToOtherClients(clientSocket, "\n");
                //out.close();//Doing this may be closing the socket.
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkIfUniqueName(String name){
        try{
            synchronized(lock2){
                for(int i = 0; i < client_names.size(); i ++){
                    if(client_names.get(i).equals(name)){
                        return false;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    
    public class TheClientHandler implements Runnable{
        private DataInputStream in = null;
        private DataOutputStream out = null;
        public Socket thesocket = null;
        public TheClientHandler(Socket socket){
            TheClientHandler.this.thesocket = socket;

        }
        @Override
        public void run(){
            //This is where we will accept multiple clients and handle them.
            try{
                synchronized(lock){//Add this client's socket to out list of clients
                    clients.add(thesocket);
                }
                
                while(thesocket.isConnected()){
                    in = new DataInputStream(new BufferedInputStream(thesocket.getInputStream()));//for input from client
                    out = new DataOutputStream(thesocket.getOutputStream());//for output to client
                    String helper = "Client accepted\nProvide your user name: \n";
                    System.out.printf(helper);
                    out.writeUTF(helper);
                  //  writeToOtherClients(thesocket, helper);//need Socket, and string

                    String line = "";
                    line = in.readUTF();//Get the username.
                    String name = line;

                    if(name.length() == 0){
                        out.writeUTF("You entered an empty username, so the connection is now closed, try again.\n");
                        out.writeUTF("Enter Bye to end your application.\n");
                        System.out.print("A Client was rejected for an empty username\n");
                        writeToOtherClients(thesocket, "A Client was rejected due to an empty username.\n");
                        out.close();
                        in.close();
                        return;
                    }

                    if(!checkIfUniqueName(name)){//If name is not unique, then reject the client
                        String s = "You entered a username already in use, so you are rejected from this server\n";
                        String r = "Type Bye, to end this application\n";
                        System.out.print(s);
                        System.out.print(r);
                        writeToOtherClients(thesocket, "A client was rejected due to tyring to use an already in use username.\n");
                        out.writeUTF(s);
                        out.writeUTF(r);
                        out.close();
                        in.close();
                        return;
                    }

                    synchronized(lock2){//Add name to list of client names
                        client_names.add(name);
                    }
                    //We could also add something here later that makes sure that each user name is unique.
                    helper = "Hi " + name + ", and welcome to the chat server\nType Bye when you want to leave.\n";
                    System.out.print(helper);
                    writeToOtherClients(thesocket, helper);//Let the other clients know of who just joined the server
                    out.writeUTF(helper);

                    while (!line.equals("Bye")) {//This is where you just write to the other clients, and not this client.
                        line = in.readUTF();
                        
                        if(line.equals("AllUsers"))
                            displayActiveUsers(thesocket);

                        helper = name + ":" + " " + line + "\n";
                        System.out.print(helper);//output from server
                        //out.writeUTF(helper);//Don't output to the client what they just wrote.
                        writeToOtherClients(thesocket, helper);
                    }
                    helper = "Connection over, " + name + " has left, goodbye.\n";
                    System.out.print(helper);
                    //out.writeUTF(helper);//Don't write to the client itself that they have left.
                    synchronized(lock){//Do this so you don't write the message in helper to disconnected clients.
                        clients.remove(thesocket);
                    }
                    synchronized(lock2){//Remove this client's name from the name list too
                        client_names.remove(name);
                    }
                    writeToOtherClients(thesocket, helper);//Maybe include this in the synchronized block and wrap around an if(!list.empty)
                    out.close();
                    socket.close();
                    in.close();
                   // modifyArray(myThreadNumber, 0);

                }

            }catch(Exception e){
                //e.printStackTrace();
                //System.out.println("Error occured here.");
                

            }

        }






    }

    public static void main(String args[]) 
    { 

        TheServer server = new TheServer(Integer.parseInt(args[0]));//Port number is given by a comman line argument
        //there should be a game instance variable, and you should construct and call the game here
        if(playerQueue.size() == 2){
            synchronized(gameLock){
                Game game = new Game(playerQueue.take(), playerQueue.take());

            }
            while(true){
                synchronized(gameLock){
                if(game.checkWinCondition(player1, player2) != "No winner yet")
                    break;

                }
            }

        }
        
    }
    
    
    
}
