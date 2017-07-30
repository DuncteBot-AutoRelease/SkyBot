package me.duncte123.skybot.commands.fun;

import me.duncte123.skybot.Command;
import me.duncte123.skybot.utils.AirUtils;
import me.duncte123.skybot.utils.URLConnectionReader;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DogCommand extends Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String base = "https://random.dog/";
        try {
            String jsonString = URLConnectionReader.getText(base + "woof");
            String finalS = base + jsonString;

            if (finalS.contains(".mp4")) {
                event.getTextChannel().sendMessage(AirUtils.embedField("A video", "[OMG LOOK AT THIS CUTE VIDEO](" + finalS + ")")).queue();
            } else {
                event.getTextChannel().sendMessage(AirUtils.embedImage(finalS)).queue();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            event.getChannel().sendMessage(AirUtils.embedMessage("**[OOPS]** Something broke, blame duncte")).queue();
        }

    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "here is a dog.";
    }
}
