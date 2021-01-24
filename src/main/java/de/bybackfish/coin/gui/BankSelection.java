package de.bybackfish.coin.gui;

import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.IPlayerBank;
import de.bybackfish.coin.api.impl.CoinAPI;
import de.bybackfish.coin.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class BankSelection implements InventoryProvider {


    public final int[][] itemSlots = {{1, 1}, {1, 3}, {1, 5}, {1, 7}, {4, 1}, {4, 3}, {4, 5}, {4, 7}}; // SLOTS WHERE THE ITEMS ARE


    @Override
    public void init(Player player, InventoryContents inventoryContents) {


        IBank bank = new CoinAPI().getPlayerCoins(player.getUniqueId());
        List<String> bankList = bank.getBankNames();

        inventoryContents.fill(ClickableItem.of(buildPlaceholder(), e -> {

        }));

        System.out.println(bankList.size());

        for (int i = 0; i < bankList.size(); i++) {

            inventoryContents.set(itemSlots[i][0], itemSlots[i][1], ClickableItem.of(buildItem(bankList.get(i), bank), e -> {

                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                InventoryUtil.openManagerInventory(player, bank.getBank(name));


            }));

        }


    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public ItemStack buildItem(String name, IBank bank) {
        IPlayerBank playerBank = bank.getBank(name);

        ItemStack itemStack = new ItemStack(Material.DISPENSER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + name);
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Current Balance: $" + playerBank.getCoins(), "", ChatColor.GOLD + "Click to select this Bank"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildPlaceholder() {

        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Placeholder");
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }

}
