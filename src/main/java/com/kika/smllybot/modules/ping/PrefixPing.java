package com.kika.smllybot.modules.ping;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrefixPing extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw().toLowerCase().trim();
        String prefix = Main.prefixes[0].toLowerCase();

        Set<String> commands = Set.of("ping", "pong", "пинг", "понг");

        if (content.startsWith(prefix)) {
            content = content.substring(prefix.length()).trim();
        }

        if (commands.contains(content)) {

            event.getJDA().getRestPing().queue((time) -> {
                String response = I18n.get("ping", "ping.response", lang);

                event.getChannel().sendMessageFormat(response, time).queue();

                System.out.println(GREEN + I18n.get("ping", "log.success", lang));
            });
        }
        System.out.println("✅ Обработана команда пинг");
    }

}

