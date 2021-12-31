package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy extends PrettyDash{

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private boolean running = false;
    private String packet = "";

    //Variables to stroe the robots position
    private double estX = 0.0;
    private double estY = 0.0;
    private double estTheta = 0.0;

    public Proxy(){
        try{
            serverSocket = new ServerSocket(1234);
            DriverStation.reportWarning("Server Started", false);
            DriverStation.reportWarning("Waiting client...", false);
            int i = 0;
            while(i > 20){
                i = i + 1;
                socket = serverSocket.accept();
                DriverStation.reportWarning("Client accepted", false);
                input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                packet = input.toString();
                metric("VSLAM Pose: ", packet);
                DriverStation.reportWarning("Jetson Message: " + packet, false);
            }
            stop();
        }catch(Exception e){
            DriverStation.reportError(e.toString(), false);
            stop();
        }
    }

    public String getRawPacket(){
        //Get the raw
        return packet;
    }

    private void proccessPacket(){
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
    
    public Pose2d getPoseMeters(){
        //Create a 2d pose of where on the field we think we are.
        proccessPacket();
        Rotation2d thetaRot = new Rotation2d(estTheta);
        Pose2d VEstPose = new Pose2d(estX, estY, thetaRot);
        return VEstPose;
    }
    
    public boolean isServerRunning(){
        //Check if the server is running
        return running;
    }

    public void stop(){
        //Stop the server form running
        try{
            socket.close();
            serverSocket.close();
        }catch(Exception e){

        }
    }
}