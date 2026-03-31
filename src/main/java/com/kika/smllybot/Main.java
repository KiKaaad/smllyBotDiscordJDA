package com.kika.smllybot;

import com.kika.smllybot.modules.ping.PrefixPing;
import com.kika.smllybot.modules.ping.SlashPing;
import com.kika.smllybot.utils.I18n;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import static com.kika.smllybot.utils.colors.GREEN;
import static com.kika.smllybot.utils.formatting.BOLD;

public class Main implements EventListener {
    String lang = "by";

    public static final String[] prefixes = {"JDA!"};

    public static void main(String[] args) throws InterruptedException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println(BOLD+GREEN + "✅ DB:ON | База данных PostgreSQL подключена!");
            UserTable.createTable();
        } catch (SQLException e) {
            System.err.printf(BOLD+RED + "⛔ DB:ERR | Не удалось связаться с базой!");
            System.err.printf(BOLD+RED + "💬 Причина: " + e.getMessage());
            return;
        }

        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("Token");

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Main())
                // Pinger
                .addEventListeners(new PrefixPing())
                .addEventListeners(new SlashPing())
                // User
                .addEventListeners(new GlobalProfile())
                .build();

        jda.awaitReady();
        slashCmdInfo.registerCommands(jda);
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            String botStarted = I18n.get("default", "bot.started", lang);

            System.out.printf(BOLD + GREEN + botStarted + "\n\n");
        }
    }

}