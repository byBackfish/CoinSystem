package de.bybackfish.coin.listener;

import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.impl.CoinAPI;
import de.bybackfish.coin.config.Config;
import de.bybackfish.coin.config.ConfigLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class PlayerChatEvent implements Listener {

    public static HashMap<Player, String> actions = new HashMap<>(); // SHITCODE BUT ANVIL GUIS ARENT WORKING IN 1.16.5 SO I HAD TO SOLVE IT LIKE THIS OR SIGNS. SIGNS ARE KINDA BUGGY SO I CHOSE THIS

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        Config config = ConfigLoader.getConfig("messages");

        if (actions.containsKey(event.getPlayer())) {
            String action = actions.get(event.getPlayer());

            event.setCancelled(true);
            actions.remove(event.getPlayer());

            IBank bank = new CoinAPI().getPlayerCoins(event.getPlayer().getUniqueId());
            String[] args = action.split(":");
            if (args[0].equalsIgnoreCase("rename")) {

                if (!bank.hasBank(event.getMessage())) {
                    bank.renameBank(args[1], event.getMessage());
                    event.getPlayer().sendMessage(config.get("renamesuccessfull").toString());
                    return;
                } else {
                    event.getPlayer().sendMessage(config.get("banknotfound").toString());
                }
            } else if (args[0].equalsIgnoreCase("transfer")) {


                if (bank.hasBank(event.getMessage())) {
                    bank.getBank(args[1]).transfer(bank.getBank(event.getMessage()), bank.getBank(args[1]).getCoins());
                    event.getPlayer().sendMessage(config.get("transfersuccessfull").toString());
                    return;
                } else {
                    event.getPlayer().sendMessage(config.get("banknotfound").toString());
                }


            }


        }

    }


}
