package de.bybackfish.coin.config;

import java.util.HashMap;

public class ConfigLoader {

    private static final HashMap<String, Config> configMap = new HashMap<>();

    public ConfigLoader() {
    }

    public static Config getConfig(String name) {

        if (configMap.containsKey(name)) return configMap.get(name);

        return createConfig(name);

    }

    public static Config createConfig(String name) {

        configMap.put(name, new Config(name));

        return getConfig(name);

    }

    public static boolean saveAll() {
        configMap.forEach((name, config) -> {

            config.save();
        });

        return true;
    }

}
