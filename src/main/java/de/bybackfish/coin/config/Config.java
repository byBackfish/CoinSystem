package de.bybackfish.coin.config;

import de.bybackfish.coin.CoinSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class Config implements Listener {

    private final File file;
    private FileConfiguration configuration;

    public Config(String name) {


        file = new File(CoinSystem.getInstance().getDataFolder(), name + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);

        configuration.options().copyDefaults(true);
        configuration.addDefault("schema", "coins");
        save();

    }

    public void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public Object get(String path) {
        if (configuration.contains(path))
            return configuration.get(path);

        return "The Object " + path + " could not be Found! Please report to the Server Admins";
    }

    public String getString(String path) {
        return get(path).toString();
    }

    public String getOrDefault(String path, String fallback) {
        return get(path).toString().startsWith("The Object") ? fallback : get(path).toString();
    }


    public int getInt(String path) {
        return Integer.parseInt(get(path).toString());
    }

    public boolean set(String path, Object content) {

        configuration.set(path, content);
        return true;
    }

    public String format(String path, Object... objects) {

        String out = path;
        for (int i = 0; i < objects.length; i++) {
            out = out.replace("%" + i + "%", objects[i].toString());
        }

        return out;


    }

    public boolean save() {
        try {
            configuration.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
