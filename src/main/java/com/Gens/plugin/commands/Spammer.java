package com.Gens.plugin.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;

import java.util.regex.Pattern;

public class Spammer extends Command {

    public Spammer(String permission, String[] keys) {
       super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        Habbo habbo = gameClient.getHabbo();

        if(params.length < 2){
            habbo.whisper(Emulator.getTexts().getValue("commands.error.spammer.shortParam"));
        } else {
            String regex = "[0-9]+";
            String data = params[1];
            if(data.matches(regex)){
                int spamNumber = Integer.parseInt(data);
                if(spamNumber < 1 || spamNumber > 15){
                    habbo.whisper(Emulator.getTexts().getValue("commands.error.spammer.longCapacity"));
                } else {
                    habbo.whisper(Emulator.getTexts().getValue("commands.success.spammer.enabled").replace("%count%",String.valueOf(spamNumber)));
                    for(int m = 0; m < spamNumber; m++){
                        habbo.talk(Emulator.getTexts().getValue("commands.success.spammer.spamWord"));
                    }
                }
            } else {
                habbo.whisper(Emulator.getTexts().getValue("commands.error.spammer.notNumber"));
            }
        }

        return true;
    }
}
