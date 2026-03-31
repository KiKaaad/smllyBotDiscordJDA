package com.kika.smllybot.modules.ping;

import com.kika.smllybot.Main;
import com.kika.smllybot.utils.I18n;
import com.kika.smllybot.utils.I18nRequest;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Set;

import static com.kika.smllybot.utils.colors.GREEN;

public class PrefixPing extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw().toLowerCase().trim();
        String prefix = Main.prefixes[0].toLowerCase();

        Set<String> commands = Set.of("ping", "pong", "пинг", "понг");

        if (content.startsWith(prefix)) {
            content = content.substring(prefix.length()).trim();
        }

        if (commands.contains(content)) {

            event.getJDA().getRestPing().queue((time) -> {
                var getString = new I18nRequest("ru", "modules", "ping", "ping", "ping.response");
                String response = I18n.get(getString);

                event.getChannel().sendMessageFormat(response, time).queue();

                var logReq = new I18nRequest("ru", "logger", "CommandUsed", "ping.used");
                System.out.println(GREEN + I18n.get(logReq));

            });
        }
    }
}
