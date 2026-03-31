package com.kika.smllybot.modules.user;

import com.kika.smllybot.Main;
import com.kika.smllybot.database.postgresql.users.User;
import com.kika.smllybot.database.postgresql.users.UserTable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;
import java.util.Set;

public class GlobalProfile extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw().toLowerCase().trim();
        String prefix = Main.prefixes[0].toLowerCase();

        Set<String> commands = Set.of("анкета", "анкет а");

        if (content.startsWith(prefix)) {
            content = content.substring(prefix.length()).trim();
        }

        if (commands.contains(content)) {

            var author = event.getAuthor();
            long discordId = author.getIdLong();

            String name = author.getName();
            String discordCreated = author.getTimeCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            User dbUser = UserTable.getOrCreateUser(discordId);

            String response = String.format("""
                    Анкета %s
                    created discord: %s
                    id locale: %d
                    id discord: %d
                    """, name, discordCreated, dbUser.internalId(), dbUser.discordId()
            );

            event.getChannel().sendMessage(response).queue();
        }
    }
}

