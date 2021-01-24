package de.bybackfish.coin.gui;

import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.IPlayerBank;
import de.bybackfish.coin.api.impl.CoinAPI;
import de.bybackfish.coin.listener.PlayerChatEvent;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BankSettings implements InventoryProvider {
    private final IPlayerBank bank;

    public BankSettings(IPlayerBank iPlayerBank) {
        this.bank = iPlayerBank;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        IBank iBank = new CoinAPI().getPlayerCoins(player.getUniqueId());

        inventoryContents.fill(ClickableItem.empty(buildPlaceholder()));

        inventoryContents.set(1, 2, ClickableItem.of(buildTransferItem(), e -> {

            player.closeInventory();
            player.sendMessage("Please put in the Name of the Bank you want to transfer the Money to");
            PlayerChatEvent.actions.put(player, "transfer:" + bank.getName());

        }));

        inventoryContents.set(1, 4, ClickableItem.of(buildRenameItem(), e -> {

            player.closeInventory();
            player.sendMessage("Please put in the new Name for the Bank in chat");
            PlayerChatEvent.actions.put(player, "rename:" + bank.getName());

        }));

        inventoryContents.set(1, 6, ClickableItem.of(buildDeleteItem(), e -> {
            if (bank.getCoins() <= 0) {
                iBank.deleteBank(bank.getName());
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                player.closeInventory();
            }
        }));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public ItemStack buildDeleteItem() {
        ItemStack itemStack = new ItemStack(Material.TNT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Delete this Bank Account");
        itemMeta.setLore(Arrays.asList(ChatColor.RED + "You cant undo this!", ChatColor.RED + "You cant do this if you still have Coins on this Account"));
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

    public ItemStack buildTransferItem() {

        ItemStack itemStack = new ItemStack(Material.COMPARATOR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Transfer Money");
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Transfer Money from one to another Account"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }

    public ItemStack buildRenameItem() {

        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY + "Rename");
        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Edit the Name of your Bank Account"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;


    }

    public ItemStack buildNametagItem(String name) {

        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }
}
