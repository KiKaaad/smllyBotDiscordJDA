package com.kika.smllybot.modules.ping;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase("!пинг")) {
            long ping = event.getJDA().getGatewayPing();

            event.getChannel().sendMessage("Понг! 🏓 " + ping + "мс").queue();
        }
    }
}
