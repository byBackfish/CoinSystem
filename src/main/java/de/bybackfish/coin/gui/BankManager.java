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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankManager implements InventoryProvider {

    private final IPlayerBank bank;


    public BankManager(IPlayerBank bank) {
        this.bank = bank;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        inventoryContents.fill(ClickableItem.of(buildPlaceholder(), (e) -> {
        }));

        inventoryContents.set(1, 2, ClickableItem.of(buildDepositItem(), e -> {
            InventoryUtil.openDepositInventory(player, bank);
        }));

        inventoryContents.set(1, 4, ClickableItem.of(buildWithdrawItem(), e -> {
            InventoryUtil.openWithdrawInventory(player, bank);
        }));

        inventoryContents.set(1, 6, ClickableItem.of(buildHistoryItem(), e -> {
        }));
        inventoryContents.set(2, 8, ClickableItem.of(buildSettingsItem(), e -> {
            InventoryUtil.openSettingsInventory(player, bank);
        }));


    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public ItemStack buildPlaceholder() {

        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Placeholder");
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }

    public ItemStack buildDepositItem() {

        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Deposit Money");
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Current Balance: $" + bank.getCoins(), "", ChatColor.GOLD + "Click to deposit Money"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildWithdrawItem() {

        ItemStack itemStack = new ItemStack(Material.DROPPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Withdraw Money");
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Current Balance: $" + bank.getCoins(), "", ChatColor.GOLD + "Click to withdraw Money"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildSettingsItem() {

        ItemStack itemStack = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Bank Settings");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildHistoryItem() {

        List<String> history = bank.getHistory();
        List<String> lore = new ArrayList<>();


        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Bank History");
        itemMeta.setLore(history);
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }


}
