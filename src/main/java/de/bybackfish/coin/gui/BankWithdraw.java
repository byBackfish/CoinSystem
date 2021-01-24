package de.bybackfish.coin.gui;

import de.bybackfish.coin.api.IPlayerBank;
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

public class BankWithdraw implements InventoryProvider {

    private final IPlayerBank bank;

    public BankWithdraw(IPlayerBank bank) {
        this.bank = bank;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        inventoryContents.fill(ClickableItem.empty(buildPlaceholder()));

        inventoryContents.set(1, 2, ClickableItem.of(buildWithdrawItem("all of your Money", 64), e -> {
            bank.withdrawCoins(bank.getCoins());
            InventoryUtil.openManagerInventory(player, bank);
        }));

        inventoryContents.set(1, 4, ClickableItem.of(buildWithdrawItem("50% of your Money", 64), e -> {
            bank.withdrawCoins(bank.getCoins() / 2);
            InventoryUtil.openManagerInventory(player, bank);
        }));

        inventoryContents.set(1, 6, ClickableItem.of(buildWithdrawItem("20% of your Money", 64), e -> {
            bank.withdrawCoins(bank.getCoins() / 5);
            InventoryUtil.openManagerInventory(player, bank);
        }));


    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public ItemStack buildWithdrawItem(String format, int amount) {

        ItemStack itemStack = new ItemStack(Material.DROPPER, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Withdraw " + format);
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Current Balance: $" + bank.getCoins(), "", ChatColor.GOLD + "Click to withdraw Money"));
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
