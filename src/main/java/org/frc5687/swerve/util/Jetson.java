package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.frc5687.swerve.Constants;

public class Jetson extends Thread{

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
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
        listener = new Listener();
        listener.run();
    }

    public String getRawPacket(String packet){
        //Get the raw
       return packet;
    }

    private void proccessPacket(String packet){
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

    public void updateConsole(int data){
        DriverStation.reportWarning("Jetson readout: " + data, false);
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

    class Listener implements Runnable{
        //Start server
        private Socket socket;
        private InputStream stream;
        @Override
        public void run(){
            try{
                DriverStation.reportWarning("Waiting for Jetson on " + Constants.Jetson.PORT + "...", false);
                socket = serverSocket.accept();
                DriverStation.reportWarning("Jetson connected", false);
                byte[] data = new byte[1024];
                DriverStation.reportWarning("Starting to read from Jetson", false);
                while(true){
                    stream = socket.getInputStream();
                    updateConsole(stream.read(data));
                    DriverStation.reportWarning("Jetson: " + data, false);
                    running = true;
                }
            }catch(Exception e){
                DriverStation.reportWarning(e.toString(), true);
                running = false;
            }
        }
    }
}