package com.example.alcatraz_server;

import com.example.alcatraz_server.data.Lobby;
import com.example.alcatraz_server.data.Player;
import com.example.alcatraz_server.rest.ClientCaller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private Lobby lobby;
    private ClientCaller clientCaller = new ClientCaller();

    @PostMapping("/")
    public String createLobby(
            @RequestParam(value = "amount") int playersAmount,
            @RequestBody Player player
    ) {
        if (lobby != null) {
            return "Lobby has already been created";
        } else {
            if(1 < playersAmount && playersAmount < 5){
                List<Player> playersList = new ArrayList<>();
                playersList.add(player);
                lobby = new Lobby(playersAmount, playersList);
                lobby.setHostPlayer(player);
                System.out.println("Lobby created"); //Todo delete
                return "Lobby was successfully created"; //TODO change return
            }
            else {
                return "Amount of players must be in range from 2 to 4"; //TODO change return
            }
        }
    }

    @PostMapping("/lobby")
    public String addPlayerToLobby(@RequestBody Player player) {
        if (lobby.getListOfPlayers().size() < lobby.getAmountOfPlayers()) {
            if (isUniqueUsername(player.getUsername())) {
                lobby.addPlayer(player);
                return "Player was added successfully";
            } else {
                return "Player with such name is in the lobby.";
            }
        } else {
            return "Lobby is full already";
        }
    }

    @DeleteMapping("/lobby")
    public String leaveLobby(@RequestParam(value = "username") String username) { //TODO mb better to make it via port
        lobby.deletePlayer(username);
        return "Successfully left lobby";
    }

    @PostMapping("/start")
    public void startGame(@RequestParam(value = "username") String username) throws IOException {
        if (lobby.getListOfPlayers().size() == lobby.getAmountOfPlayers() && username.equals(lobby.getHostPlayer().getUsername())) {
            clientCaller.sendPlayersList(lobby.getListOfPlayers());
            System.out.println("Game has started");
            lobby = null;
        } else {
            //Mb change return type of method to return error message
        }
    }

    private boolean isUniqueUsername(String username) {
        return lobby.getListOfPlayers().stream().filter(player -> player.getUsername().equals(username)).toList().isEmpty();
    }
}
