package com.example.alcatraz_server.rest;

import com.example.alcatraz_server.data.Player;
import com.example.alcatraz_server.utils.JsonConverter;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class ClientCaller {
    private final String BASE_URL = "http://127.0.0.1:";
    static final MediaType JSON = MediaType.get("application/json");
    private final OkHttpClient client = new OkHttpClient();
    private final JsonConverter jsonConverter = new JsonConverter();

    public void sendPlayersList(List<Player> players) throws IOException {
        for (Player p : players) {
            sendClients(p, players);
        }
    }

    private void sendClients(Player player, List<Player> otherPlayers) throws IOException {
        String json = jsonConverter.toJson(otherPlayers);
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + player.getPort())
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
