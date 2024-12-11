package com.example.alcatraz_server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
    private String username;
    private int port;

    @JsonCreator
    public Player(
            @JsonProperty("username") String username,
            @JsonProperty("port") int port) {
        this.username = username;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
