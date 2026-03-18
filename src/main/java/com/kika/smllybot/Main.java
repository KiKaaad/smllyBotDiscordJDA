package com.kika.smllybot;

import com.kika.smllybot.modules.ping.PrefixPing;
import com.kika.smllybot.modules.ping.SlashPing;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import static com.kika.smllybot.fun.colors.GREEN;
import static com.kika.smllybot.fun.formatting.BOLD;

public class Main implements EventListener {

    public static final String[] prefixes = {"JDA!", "java!"};

    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("Token");

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Main())
                // Pinger
                .addEventListeners(new PrefixPing())
                .addEventListeners(new SlashPing())
                // Economy
                .build();

        jda.awaitReady();
        slashCmdInfo.registerCommands(jda);
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println(BOLD + GREEN + "✅ Бот запущен");
        }
    }

}