package com.Gens.plugin;

import com.Gens.plugin.commands.Spammer;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends HabboPlugin implements EventListener {

    public static Main INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this,this);
        if(Emulator.isReady){
            this.checkDatabase();
        }
    }

    @Override
    public void onDisable() {


    }

    @Override
    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    private boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn)
    {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
            {
                statement.execute();
                return true;
            }
        }
        catch (SQLException e)
        {}

        return defaultReturn;
    }

    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {

        INSTANCE.checkDatabase();

        Emulator.getTexts().register("commands.keys.cmd_spammerCD", "spammer");
        Emulator.getTexts().register("commands.description.cmd_spammerCD", ":spammer");

        // Success Messages
        Emulator.getTexts().register("commands.success.spammer.enabled", "Spammer baslatildi. %count% kere spam atilacak.");
        Emulator.getTexts().register("commands.success.spammer.spamWord", "Burası en iyi hotel, burada reklam yapmaya çalışma !!");
        // Error Messages
        Emulator.getTexts().register("commands.error.spammer.shortParam", "Komut eksik kullanildi. :spammer (spam sayisi) -> :spammer 10");
        Emulator.getTexts().register("commands.error.spammer.notNumber", "Spam sayisi harf olarak girilemez.");
        Emulator.getTexts().register("commands.error.spammer.longCapacity", "Spam sayisi 1 ila 15 arasi olabilir.");

        CommandHandler.addCommand(new Spammer("cmd_spammerCD", Emulator.getTexts().getValue("commands.keys.cmd_spammerCD").split(";")));


        System.out.println(Emulator.getTexts().getValue("commands.success.spammer.enabled"));
        System.out.println("[" + "OFFICIAL PLUGIN"  + "] " + " Spammer Bot (1.0.0) has official loaded!");
    }

    public void checkDatabase() {
        boolean reloadPermissions = false;
        reloadPermissions = this.registerPermission("cmd_spammerCD", "'0', '1', '2'", "0", reloadPermissions);
        if (reloadPermissions)
        {
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        }
    }



}

