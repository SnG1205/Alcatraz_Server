package com.example.alcatraz_server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Lobby {
    private Player hostPlayer;
    private int amountOfPlayers;
    private List<Player> listOfPlayers;

    //public Lobby(){}

    public Lobby(int amountOfPlayers, List<Player> listOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
        this.listOfPlayers = listOfPlayers;
    }

    @JsonCreator
    public Lobby(
            @JsonProperty("hostPlayer") Player hostPlayer,
            @JsonProperty("amountOfPlayers") int amountOfPlayers,
            @JsonProperty("listOfPlayers") List<Player> listOfPlayers
    ) {
        this.amountOfPlayers = amountOfPlayers;
        this.listOfPlayers = listOfPlayers;
        this.hostPlayer = hostPlayer;
    }

    public void addPlayer(Player player){
        listOfPlayers.add(player);
    }

    public void deletePlayer(String username){ //Todo add delete
        //listOfPlayers.remove(1);
        listOfPlayers.removeIf(player -> player.getUsername().equals(username));
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
