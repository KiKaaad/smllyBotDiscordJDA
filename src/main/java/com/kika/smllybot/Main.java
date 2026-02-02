package com.kika.smllybot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main {

    private final Dotenv config;

    private final ShardManager shardManager;

    public Main() throws LoginException {
        // Загрузка токена из .env
        config = Dotenv.configure().load();
        String Token = config.get("Token");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.watching("42 ПЯТОРКА ЭЩКЕРЕ"));
        shardManager = builder.build();
    }

    public Dotenv getconfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Main bot = new Main();
        } catch (LoginException e) {
            System.out.println("Ошибка: Токен недействителен");
        }
    }
}
