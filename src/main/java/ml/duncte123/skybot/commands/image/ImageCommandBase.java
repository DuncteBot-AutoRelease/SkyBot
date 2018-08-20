/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017 - 2018  Duncan "duncte123" Sterken & Ramid "ramidzkh" Khan & Maurice R S "Sanduhr32"
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ml.duncte123.skybot.commands.image;

import me.duncte123.botCommons.messaging.MessageUtils;
import ml.duncte123.skybot.objects.command.Command;
import ml.duncte123.skybot.objects.command.CommandCategory;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static me.duncte123.botCommons.messaging.MessageUtils.sendMsg;

public abstract class ImageCommandBase extends Command {

    private static final String dir = "user_avatars";

    boolean canSendFile(GuildMessageReceivedEvent event) {
        if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_ATTACH_FILES)) {
            return true;
        } else {
            MessageUtils.sendMsg(event, "I need permission to upload files in this channel in order for this command to work");
            return false;
        }
    }

    boolean hasArgs(GuildMessageReceivedEvent event, List<String> args) {
        if (args.isEmpty()) {
            sendMsg(event, "Too little arguments");
            return false;
        }
        return true;
    }

    boolean doAllChecks(GuildMessageReceivedEvent event, List<String> args) {
        return doAllChecksButNotTheArgsBecauseWeDontNeedThem(event) && hasArgs(event, args);
    }

    boolean doAllChecksButNotTheArgsBecauseWeDontNeedThem(GuildMessageReceivedEvent event) {
        event.getChannel().sendTyping().queue();
        return canSendFile(event) && isUserOrGuildPatron(event);
    }

    File getFile() {
        return new File(dir + "/" + getName() + "_" + System.currentTimeMillis() + ".png");
    }

    void handleBasicImage(GuildMessageReceivedEvent event, InputStream image) {
        try {
            File img = getFile();
            Files.copy(image, img.toPath(), StandardCopyOption.REPLACE_EXISTING);
            event.getChannel().sendFile(img).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.PATRON;
    }
}
