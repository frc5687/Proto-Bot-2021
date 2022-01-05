package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.frc5687.swerve.Constants;

public class Jetson extends Thread{

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream stream;
    private DataInputStream input;
    private boolean running = false;
    private boolean reading = false;
    private String line = " ";
    private Listener listener;
    private DriverInterface driver;
    private boolean end = false;
    //Variables to stroe the robots position
    private double estX = 0.0;
    private double estY = 0.0;
    private double estTheta = 0.0;

    public Jetson(){
        driver = new DriverInterface();
    }

    public void startListening(){
        try {
            serverSocket = new ServerSocket(Constants.Jetson.PORT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        listener = new Listener();
        listener.run();
    }

    public String getRawPacket(){
        //Get the raw
       return line;
    }

    public double[] getDataArray(){
        double[] hold = {estX, estY, estTheta};
        return hold;
    }

    public void proccessPacket(String packet){
        //{x, y, theta}
        if(packet.contains("T") == true){
            //It's a translation data packet
            driver.warn("Translation Data Found: " + packet);
            
        }else{
            if(packet.contains("R") == true){
                //It's a rotation data packet
                driver.warn("Rotation Data Found: " + packet);
            }else{
                //Cannot ID incomeing data packet
                driver.error("Unidentified Data Packet Found: " + packet);
            }
        }
    }

    public Pose2d getPose(){
        Rotation2d estRot = new Rotation2d(estTheta);
        Pose2d pose = new Pose2d(estX, estY, estRot);
        return pose;
    }

    public boolean isServerRunning(){
        //Check if the server is running
        return running;
    }

    public void stopp(){
        //Stop the server form running
        try{
            socket.close();
            serverSocket.close();
        }catch(Exception e){

        }
    }

    public void periodic(){
        if(isServerRunning() == true){
            getPose();
        }else{

        }
    }

    class Listener{
        //Start server
        public void run(){
            new Thread(() -> {
                try{
                    byte[] messageByte = new byte[1000];
                    String dataString = "";
                    driver.warn("Starting Jetson Proxy Server");
                    driver.warn("Waiting For Jetson Connection..");
                    socket = serverSocket.accept();
                    driver.warn("Jetson Connection Accepted");
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    driver.warn("Reading From Jetson...");
                    while(!end){
                        int bytesRead = in.read(messageByte);
                        dataString += new String(messageByte, 0, bytesRead);
                        proccessPacket(dataString);
                        //Flush pose data
                        dataString = "";
                    }
                }catch(Exception e){
                    DriverStation.reportWarning(e.toString(), true);
                    running = false;
                }
            }).start();
        }
    }
}