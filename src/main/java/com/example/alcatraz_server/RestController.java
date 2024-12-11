package com.example.alcatraz_server;

import com.example.alcatraz_server.data.Lobby;
import com.example.alcatraz_server.data.Player;
import com.example.alcatraz_server.rest.ClientCaller;
import com.example.alcatraz_server.spread_server.Server;
import com.example.alcatraz_server.spread_server.ServerListener;
import com.example.alcatraz_server.utils.JsonConverter;
import com.example.alcatraz_server.utils.RandomNum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import spread.SpreadException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private String username = String.valueOf(RandomNum.randInt()) ;
    private Lobby lobby;
    private ServerListener serverListener = new ServerListener(lobby, username);
    private Server server = new Server(username, serverListener);
    private ClientCaller clientCaller = new ClientCaller();
    private JsonConverter jsonConverter = new JsonConverter();

    @PostMapping("/")
    public String createLobby(
            @RequestParam(value = "amount") int playersAmount,
            @RequestBody Player player
    ) throws SpreadException, JsonProcessingException {
        if (lobby != null) {
            return "Lobby has already been created";
        } else {
            if(1 < playersAmount && playersAmount < 5){
                List<Player> playersList = new ArrayList<>();
                playersList.add(player);
                lobby = new Lobby(playersAmount, playersList);
                lobby.setHostPlayer(player);
                System.out.println("Lobby created"); //Todo delete
                server.send("lobby_creation!" + jsonConverter.toJson(lobby));
                return "Lobby was successfully created";//TODO change return
            }
            else {
                return "Amount of players must be in range from 2 to 4"; //TODO change return
            }
        }
    }

    @PostMapping("/lobby")
    public String addPlayerToLobby(@RequestBody Player player) throws JsonProcessingException, SpreadException {
        if (lobby.getListOfPlayers().size() < lobby.getAmountOfPlayers()) {
            if (isUniqueUsername(player.getUsername())) {
                lobby.addPlayer(player);
                server.send("lobby_join!" + jsonConverter.toJson(player));
                return "Player was added successfully";
            } else {
                return "Player with such name is in the lobby.";
            }
        } else {
            return "Lobby is full already";
        }
    }

    @DeleteMapping("/lobby")
    public String leaveLobby(@RequestParam(value = "username") String username) throws SpreadException { //TODO mb better to make it via port
        lobby.deletePlayer(username);
        server.send("lobby_leave!" + username);
        if(lobby.getListOfPlayers().isEmpty()){
            lobby = null;
        }
        return "Successfully left lobby";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam(value = "username") String username) throws IOException, SpreadException {
        if (lobby.getListOfPlayers().size() == lobby.getAmountOfPlayers()) {
            if (username.equals(lobby.getHostPlayer().getUsername())){
                clientCaller.sendPlayersList(lobby.getListOfPlayers());
                System.out.println("Game has started");
                lobby = null;
                server.send("lobby_start!");
                return "Game has started";
            }
            return "You are not an owner of the lobby";
        } else {
            return "Lobby is not full yet";
        }
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    private boolean isUniqueUsername(String username) {
        return lobby.getListOfPlayers().stream().filter(player -> player.getUsername().equals(username)).toList().isEmpty();
    }
}
