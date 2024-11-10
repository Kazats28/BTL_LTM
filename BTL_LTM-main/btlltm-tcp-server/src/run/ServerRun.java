package run;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.ClientHandler;
import service.ClientManager;
import service.RoomManager;
import view.ServerView;

public class ServerRun {

    public static volatile ClientManager clientManager;
    public static volatile RoomManager roomManager;
    public static ServerView serverView;
    public static boolean isShutDown = false;
    public static ServerSocket ss;

    public ServerRun() {

        try {
            int port = 8282;
//            String ip = "192.168.1.197";
//            ss = new ServerSocket();
//            InetSocketAddress address = new InetSocketAddress(ip, port);
//            ss.bind(address);
            ss = new ServerSocket(port);
            System.out.println("Server đang lắng nghe trên: " + ss.getInetAddress() + ":" + ss.getLocalPort());

            
            // init managers
            clientManager = new ClientManager();
            roomManager = new RoomManager();
            
            // create threadpool
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    10, // corePoolSize
                    100, // maximumPoolSize
                    10, // thread timeout
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(8) // queueCapacity
            );

            // server main loop - listen to client's connection
            while (!isShutDown) {
                try {
                    // socket object to receive incoming client requests
                    Socket s = ss.accept();
                     System.out.println("+ New Client connected: " + s);

                    // create new client runnable object
                    ClientHandler c = new ClientHandler(s);
                    clientManager.add(c);
                    System.out.println("Count of client online: " + clientManager.getSize());
                    // execute client runnable
                    executor.execute(c);

                } catch (IOException ex) {
                    // Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    isShutDown = true;
                }
            }

            System.out.println("shutingdown executor...");
            executor.shutdownNow();

        } catch (IOException ex) {
            Logger.getLogger(ServerRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        serverView = new ServerView();
        serverView.setVisible(true);
        serverView.setLocationRelativeTo(null);
        
        new ServerRun();
    }
}
