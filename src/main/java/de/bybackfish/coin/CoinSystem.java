package de.bybackfish.coin;


import api.bybackfish.DB;
import api.bybackfish.Table;
import de.bybackfish.coin.api.Interest;
import de.bybackfish.coin.command.BankCommand;
import de.bybackfish.coin.config.Config;
import de.bybackfish.coin.config.ConfigLoader;
import de.bybackfish.coin.listener.PlayerChatEvent;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinSystem extends JavaPlugin {

    @Getter
    private static CoinSystem instance;

    @Getter
    private DB database;


    @Override
    public void onEnable() {
        instance = this;

        initDatabase();
        registerCommands();
        registerListener();

        new Interest();
    }

    @Override
    public void onDisable() {

    }

    public void initDatabase() {
        Config config = ConfigLoader.getConfig("database");
        database = new DB(
                config.getOrDefault("host", "localhost"),
                config.getOrDefault("username", "root"),
                config.getOrDefault("password", ""),
                config.getOrDefault("schema", "coins")
        );
        database.createTableIfNotExist(new Table("player_bank").varchar("uuid", 40).varchar("bank", 26).column("coins", Long.class).longtext("history"));

        database.createTableIfNotExist(new Table("data").varchar("last_interest", 50));
    }

    void registerCommands() {
        super.getCommand("bank").setExecutor(new BankCommand());
        super.getCommand("bank").setTabCompleter(new BankCommand());
    }

    void registerListener() {
        super.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this); // SHITCODE BUT ANVIL GUIS ARENT WORKING IN 1.16.5 SO I HAD TO SOLVE IT LIKE THIS OR SIGNS. SIGNS ARE KINDA BUGGY SO I CHOSE THIS
    }

}
