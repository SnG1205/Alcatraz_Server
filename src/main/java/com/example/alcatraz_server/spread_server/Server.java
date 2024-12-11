package com.example.alcatraz_server.spread_server;

import spread.*;

import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {

    private SpreadConnection spreadConnection;
    private SpreadGroup spreadGroup;

    public Server(String username, ServerListener serverListener) {
        try{
            spreadConnection = new SpreadConnection();
            spreadConnection.connect(InetAddress.getByName("192.168.0.215"), 4803, username, false, true);
            spreadConnection.add(serverListener);
            spreadGroup = new SpreadGroup();
            spreadGroup.join(spreadConnection, "serhii");
            System.out.println("It worked");
        } catch(SpreadException e)
        {
            System.err.println("There was an error connecting to the daemon.");
            e.printStackTrace();
        }
        catch(UnknownHostException e)
        {
            System.err.println("Can't find the daemon " + "192.168.0.215");
        }
    }

    public void send(String s) throws SpreadException {
        SpreadMessage message = new SpreadMessage();
        message.setSafe();
        message.addGroup("serhii");
        message.setData(s.getBytes());
        spreadConnection.multicast(message);
    }

    public SpreadConnection getSpreadConnection() {
        return spreadConnection;
    }

    public void setSpreadConnection(SpreadConnection spreadConnection) {
        this.spreadConnection = spreadConnection;
    }
}