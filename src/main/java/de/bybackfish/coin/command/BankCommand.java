package de.bybackfish.coin.command;

import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.IPlayerBank;
import de.bybackfish.coin.api.impl.CoinAPI;
import de.bybackfish.coin.config.Config;
import de.bybackfish.coin.config.ConfigLoader;
import de.bybackfish.coin.util.InventoryUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BankCommand implements CommandExecutor, TabCompleter {

    Config config = ConfigLoader.getConfig("messages");

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage((config.get("console").toString()));
            return true;
        }

        Player player = (Player) commandSender;
        IBank playerCoins = new CoinAPI().getPlayerCoins(player.getUniqueId());

        if (args.length == 0)
            InventoryUtil.openSelectionInventory(player);

        if (args.length == 2) {
            String command = args[0];
            String bank = args[1];

            IPlayerBank playerBank = playerCoins.getBank(bank);

            if (playerCoins.hasBank(bank)) {
                player.sendMessage(config.get("banknotexist").toString());
                return true;
            }

            switch (command) {

                case "create":
                    if (playerCoins.createBank(bank))
                        player.sendMessage(config.get("created").toString());
                    else
                        player.sendMessage(config.get("couldnotcreate").toString());
                    break;
                case "bal":
                    player.sendMessage(config.format(config.get("balance").toString(), playerBank.getCoins()));
                    break;
                case "delete":
                    if (playerBank.getCoins() <= 0) {
                        playerCoins.deleteBank(bank);
                        player.sendMessage(config.get("bankdeleted").toString());
                    } else
                        player.sendMessage(config.get("cantdelete").toString());
                    break;

            }
        }


        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Arrays.asList("create", "delete", "bal");
    }
}
