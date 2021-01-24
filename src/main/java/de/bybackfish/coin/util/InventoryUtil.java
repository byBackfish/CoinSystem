package de.bybackfish.coin.util;

import de.bybackfish.coin.api.IPlayerBank;
import de.bybackfish.coin.gui.*;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InventoryUtil {


    public static void openManagerInventory(Player player, IPlayerBank iPlayerBank) {

        player.closeInventory();
        SmartInventory.builder()
                .id("bankManager")
                .provider(new BankManager(iPlayerBank))
                .size(3, 9)
                .title(ChatColor.BLUE + "Bank Manager")
                .build().open(player);

    }

    public static void openWithdrawInventory(Player player, IPlayerBank iPlayerBank) {

        player.closeInventory();
        SmartInventory.builder()
                .id("bankWithdraw")
                .provider(new BankWithdraw(iPlayerBank))
                .size(3, 9)
                .title(ChatColor.BLUE + "Bank Withdraw")
                .build().open(player);

    }

    public static void openDepositInventory(Player player, IPlayerBank iPlayerBank) {

        player.closeInventory();
        SmartInventory.builder()
                .id("bankDeposit")
                .provider(new BankDeposit(iPlayerBank))
                .size(3, 9)
                .title(ChatColor.BLUE + "Bank Deposit")
                .build().open(player);

    }

    public static void openSelectionInventory(Player player) {

        player.closeInventory();
        SmartInventory.builder()
                .id("bankSelection")
                .provider(new BankSelection())
                .size(6, 9)
                .title(ChatColor.BLUE + "Bank Selector")
                .build().open(player);
    }

    public static void openSettingsInventory(Player player, IPlayerBank iPlayerBank) {

        player.closeInventory();
        SmartInventory.builder()
                .id("bankSettings")
                .provider(new BankSettings(iPlayerBank))
                .size(6, 9)
                .title(ChatColor.BLUE + "Bank Settings")
                .build().open(player);
    }

   

}
