/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017  Duncan "duncte123" Sterken & Ramid "ramidzkh" Khan & Maurice R S "Sanduhr32"
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

package ml.duncte123.skybot.commands.`fun`

import com.google.code.chatterbotapi.ChatterBotFactory
import com.google.code.chatterbotapi.ChatterBotSession
import com.google.code.chatterbotapi.ChatterBotType
import ml.duncte123.skybot.objects.command.Command
import ml.duncte123.skybot.objects.command.CommandCategory
import ml.duncte123.skybot.utils.AirUtils
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import org.apache.commons.lang3.StringUtils
import org.slf4j.event.Level

class ChatCommand : Command() {

    private val bot: ChatterBotSession
    private val responses = arrayOf(
        "My prefix in this guild is {PREFIX}",
        "Thanks for asking, my prefix here is {PREFIX}",
        "That should be {PREFIX}",
        "It was {PREFIX} if I'm not mistaken"
    )


    init {
        AirUtils.log("ChatCommand", Level.INFO, "Starting AI")
        this.category = CommandCategory.FUN
        //New chat bot :D
        bot = ChatterBotFactory()
                .create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477")
                .createSession()
        AirUtils.log("ChatCommand", Level.INFO, "AI has been loaded.")
    }


    override fun executeCommand(invoke: String, args: Array<out String>, event: GuildMessageReceivedEvent) {
        if(args.isEmpty()){
            sendMsg(event, "Incorrect usage: `$PREFIX$name <message>`")
            return
        }
        val time = System.currentTimeMillis()
        val message = StringUtils.join(args, " ")
        event.channel.sendTyping().queue()

        if(message.contains("prefix")) {
            sendMsg(event, "${event.author.asMention}, " + responses[AirUtils.rand.nextInt(responses.size)]
                    .replace("{PREFIX}", "`${getSettings(event.guild).customPrefix}`"))
            return
        }

        val response = bot.think(message)
        sendMsg(event, "${event.author.asMention}, $response")
        AirUtils.log(Level.DEBUG, "New response: \"$response\", this took ${System.currentTimeMillis() - time}ms")
    }

    override fun help() = "Have a chat with dunctebot\n" +
            "Usage: `$PREFIX$name <message>`"

    override fun getName() = "chat"
}