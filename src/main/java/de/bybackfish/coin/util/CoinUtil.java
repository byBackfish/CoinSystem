package de.bybackfish.coin.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoinUtil {


    public static int getItems(Player player) {

        int out = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null)
                if (item.getType() == Material.GOLD_NUGGET)
                    if (item.hasItemMeta())
                        if (item.getItemMeta().getDisplayName().contains("Coin"))
                            out += item.getAmount();

        }


        return out;
    }

    private static boolean hasItems(Player player, long amount) {
        return getItems(player) >= amount;
    }

    public static boolean removeItems(Player player, long amount) {

        if (!hasItems(player, amount)) return false;


        int removed = 0;

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null) continue;
            if (itemStack.getType() == Material.AIR) continue;
            if (itemStack.getType() != Material.GOLD_NUGGET) continue;
            if (removed >= amount) break;


            if (itemStack.hasItemMeta())
                if (itemStack.getItemMeta().hasDisplayName())
                    if (itemStack.getItemMeta().getDisplayName().contains("Coin"))
                        if (removed + itemStack.getAmount() > amount)
                            itemStack.setAmount(itemStack.getAmount() - ((int) amount - removed));
                        else {
                            player.getInventory().remove(itemStack);
                            removed += itemStack.getAmount();
                        }

        }
        player.updateInventory();
        return true;

    }

    public static void addItem(Player player, long amount) {


        ItemStack coin = buildItem();

        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(coin);
        }


    }

    private static ItemStack buildItem() {
        ItemStack itemStack = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Coin");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
