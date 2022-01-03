package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import java.io.BufferedInputStream;
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
    //Variables to stroe the robots position
    private double estX = 0.0;
    private double estY = 0.0;
    private double estTheta = 0.0;

    public Jetson(){

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
        try{
            String[] poseData = packet.split(" ");
            estX = Double.parseDouble(poseData[0]);
            estY = Double.parseDouble(poseData[1]);
            estTheta = Double.parseDouble(poseData[3]);    
        }catch(Exception e){
            DriverStation.reportError("Jetson Packet Handler: " + e.toString(), false);
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

    class Listener{
        //Start server
        public void run(){
            new Thread(() -> {
                try{
                    DriverStation.reportWarning("Waiting for Jetson on " + Constants.Jetson.PORT + "...", false);
                    socket = serverSocket.accept();
                    DriverStation.reportWarning("Jetson connected", false);
                    String data = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    DriverStation.reportWarning("Starting to read from Jetson", false);
                    while(reader.readLine() != null){
                        data = reader.readLine();
                        DriverStation.reportWarning("Jetson: " + data, false);
                        running = true;
                    }
                }catch(Exception e){
                    DriverStation.reportWarning(e.toString(), true);
                    running = false;
                }
            }).start();
        }
    }
}