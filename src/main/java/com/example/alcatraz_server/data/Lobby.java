package com.example.alcatraz_server.data;

import java.util.List;

public class Lobby {
    private Player hostPlayer;
    private int amountOfPlayers;
    private List<Player> listOfPlayers;

    public Lobby(int amountOfPlayers, List<Player> listOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
        this.listOfPlayers = listOfPlayers;
    }

    public void addPlayer(Player player){
        listOfPlayers.add(player);
    }

    public void deletePlayer(String username){ //Todo add delete
        listOfPlayers.remove(1);
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public void setAmountOfPlayers(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }
}
