package com.example.alcatraz_server.spread_server;

import com.example.alcatraz_server.data.Lobby;
import com.example.alcatraz_server.data.Player;
import com.example.alcatraz_server.utils.JsonConverter;
import spread.*;

import java.util.List;

public class ServerListener implements AdvancedMessageListener {
    private Lobby lobby;
    private JsonConverter jsonConverter = new JsonConverter();
    private String username;
    private List<String> groupMembers = List.of();
    private SpreadConnection spreadConnection;

    public ServerListener(Lobby lobby, String username) {
        if (lobby == null){
            this.lobby = new Lobby(0, List.of());
        }
        else {
            this.lobby = lobby;
        }
        this.username = username;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setSpreadConnection(SpreadConnection spreadConnection) {
        this.spreadConnection = spreadConnection;
    }

    @Override
    public void regularMessageReceived(SpreadMessage message) {
        try{
            String unsplittedMessage = new String(message.getData());
            String[] messageParts = unsplittedMessage.split("!");
            if (messageParts[0].equals("lobby_creation")){
                System.out.println(messageParts[1]);
                lobby = jsonConverter.fromJson(messageParts[1], Lobby.class);
                System.out.println("ss1");
            }
            else if (messageParts[0].equals("lobby_join")){
                System.out.println(messageParts[1]);
                lobby.addPlayer(jsonConverter.fromJson(messageParts[1], Player.class));
                System.out.println("ss2");
            }
            else if (messageParts[0].equals("lobby_leave")){
                System.out.println(messageParts[1]);
                lobby.deletePlayer(messageParts[1]);
                if(messageParts[1].equals(lobby.getHostPlayer().getUsername())){
                    lobby.setHostPlayer(null);
                }
                System.out.println("ss3");
            } else if (messageParts[0].equals("lobby_start")) {
                System.out.println(messageParts[0]);
                lobby = new Lobby(0, List.of());
                System.out.println("ss4");
            } else if (messageParts[0].equals("new_member")) {
                if(messageParts[1].equals(username)){
                    lobby = jsonConverter.fromJson(messageParts[2], Lobby.class);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void membershipMessageReceived(SpreadMessage message) {
        try{
            MembershipInfo membershipInfo = message.getMembershipInfo();
            SpreadGroup members[] = membershipInfo.getMembers();
            if(membershipInfo.isCausedByJoin()) {
                System.out.println("Server joined a group");
                String s = members[0].toString();
                if(s.equals("#" + username + "#localhost")) {
                    SpreadMessage lobbyMsg = new SpreadMessage();
                    String jsonLobby = jsonConverter.toJson(lobby);
                    lobbyMsg.setSafe();
                    lobbyMsg.addGroup("serhii");
                    lobbyMsg.setData(("new_member!" + username + "!" + jsonLobby).getBytes());
                    //spreadConnection.multicast(lobbyMsg); //Sends a message to ask for lobby
                }
                /*for(SpreadGroup group : members){
                    groupMembers.add(group.toString());
                }*/
                System.out.println(members[0]);
                System.out.println(message.getSender() + ".");
            }
            else if(membershipInfo.isCausedByDisconnect()){
                System.out.println("Server disconnected");
            }
            else if(membershipInfo.isCausedByLeave()){
                System.out.println("Server left a group");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
