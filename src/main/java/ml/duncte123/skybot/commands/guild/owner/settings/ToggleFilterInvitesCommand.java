/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017 - 2018  Duncan "duncte123" Sterken & Ramid "ramidzkh" Khan
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

package ml.duncte123.skybot.commands.guild.owner.settings;

import ml.duncte123.skybot.entities.jda.DunctebotGuild;
import ml.duncte123.skybot.objects.command.CommandContext;
import ml.duncte123.skybot.objects.guild.GuildSettings;
import org.jetbrains.annotations.NotNull;

import static me.duncte123.botcommons.messaging.MessageUtils.sendMsg;

public class ToggleFilterInvitesCommand extends SettingsBase {
    @Override
    public void run(@NotNull CommandContext ctx) {
        DunctebotGuild guild = ctx.getGuild();
        GuildSettings settings = guild.getSettings();

        boolean shouldFilterInvites = !settings.isFilterInvites();
        guild.setSettings(settings.setFilterInvites(shouldFilterInvites));

        sendMsg(ctx.getEvent(), "Filtering discord invites has been **"
            + (shouldFilterInvites ? "enabled" : "disabled") + "**");
    }

    @Override
    public String getName() {
        return "togglefilterinvites";
    }

    @Override
    public String help() {
        return "Toggles if the bot should delete messages that contain invites\n" +
            "Usage: `" + PREFIX + getName() + "`";
    }
}
