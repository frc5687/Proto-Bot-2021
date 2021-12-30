package org.frc5687.swerve.util;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy{

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private BufferedReader inputReader;
    private boolean running = false;
    private String text = "";

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
                text = input.toString();
                metric("VSLAM Pose: ", text);
                DriverStation.reportWarning("Jetson Message: " + text, false);
            }
        }catch(Exception e){
            DriverStation.reportError(e.toString(), false);
        }
    }
    
    public boolean isServerRunning(){
        //Check if the server is running
        return running;
    }

    public void stopServer(){
        try{
            socket.close();
            serverSocket.close();
        }catch(Exception e){

        }
    }
    
    public void metric(String name, String value) {
        SmartDashboard.putString(getClass().getSimpleName() + "/" + name, value);
    }
}
