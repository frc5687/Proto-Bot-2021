package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy extends Thread{

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private boolean running = false;

    //Variables to stroe the robots position
    private double estX = 0.0;
    private double estY = 0.0;
    private double estTheta = 0.0;

    public Proxy(){

    }

    public void updatePose(){
        try{
            serverSocket = new ServerSocket(5687);
            DriverStation.reportWarning("Listening", false);
            DriverStation.reportWarning("Waiting for Jetson...", false);
            socket = serverSocket.accept();
            DriverStation.reportWarning("Jetson connected", false);

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));  

            String line = " ";
            int count = 0;
            while(line != ":"){
                try{
                    byte[] hold;
                    hold = input.readNBytes(5);
                    line = new String(hold);
                    DriverStation.reportWarning(line, false);
                }catch(IOException i){
                    DriverStation.reportWarning(i.toString(), true);
                }
            }
        socket.close();
        input.close();
        }catch(Exception e){
            DriverStation.reportWarning(e.toString(), true);
        }
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
    
    public Pose2d getPoseMeters(String packet){
        //Create a 2d pose of where on the field we think we are.
        proccessPacket(packet);
        Rotation2d thetaRot = new Rotation2d(estTheta);
        Pose2d VEstPose = new Pose2d(estX, estY, thetaRot);
        return VEstPose;
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
}