package de.bybackfish.coin.api.impl;

import api.bybackfish.DB;
import api.bybackfish.Row;
import de.bybackfish.coin.CoinSystem;
import de.bybackfish.coin.api.IPlayerBank;
import de.bybackfish.coin.util.CoinUtil;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class PlayerBankFactory {

    private final DB db;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public PlayerBankFactory() {
        this.db = CoinSystem.getInstance().getDatabase();
    }

    IPlayerBank factor(String bank, UUID uuid) {

        return new IPlayerBank() {

            @Override
            public void transfer(IPlayerBank playerBank, long amount) {
                executorService.submit(() -> {
                    playerBank.addCoins(amount);
                    removeCoins(amount);
                    addHistory("Transfered Coins to " + playerBank.getName());
                });
            }

            @Override
            public void addCoins(long coins) {

                executorService.submit(() -> {
                    db.update(
                            "UPDATE player_bank SET coins = coins + ? WHERE uuid = ? AND bank = ?",
                            coins, uuid, bank);
                });
            }

            @Override
            public void removeCoins(long coins) {
                addCoins(-coins);
            }

            @Override
            public void getCoins(Consumer<Long> coinRequest) {
                executorService.submit(() -> {
                    Row row = db.selectSingleRow(
                            "SELECT * FROM player_bank WHERE uuid = ? and bank = ? ",
                            uuid, bank
                    );
                    coinRequest.accept(row.getLong("coins"));
                });
            }

            @Override
            public long getCoins() {
                Row row = db.selectSingleRow(
                        "SELECT * FROM player_bank WHERE uuid = ? and bank = ? ",
                        uuid, bank
                );
                return row.getLong("coins");
            }

            @Override
            public void setCoins(long coins) {
                executorService.submit(() -> {
                    db.update("UPDATE player_bank SET coins = ? WHERE uuid = ? and bank = ?",
                            coins, uuid, bank);

                });

            }

            @Override
            public boolean withdrawCoins(long amount) {
                if (getCoins() < amount) return false;
                if (amount > 0)
                    addHistory("Withdrew $" + amount);

                CoinUtil.addItem(Bukkit.getPlayer(uuid), amount);


                removeCoins(amount);
                return true;
            }

            @Override
            public boolean depositCoins(long amount) {

                if (amount > 0)
                    addHistory("Deposited $" + amount);

                if (!CoinUtil.removeItems(Bukkit.getPlayer(uuid), amount)) return false;

                addCoins(amount);

                return true;
            }

            @Override
            public String getName() {
                return bank;
            }

            @Override
            public List<String> getHistory() {
                Row row = db.selectSingleRow("SELECT * FROM player_bank WHERE uuid =  ? AND bank = ?", uuid, bank);

                return Arrays.asList(row.get("history").split(":"));
            }

            @Override
            public void addHistory(String format) {
                db.update("UPDATE player_bank SET history = CONCAT(?, history) WHERE uuid = ? AND bank = ?", format + ":", uuid, bank);
            }
        };
    }
}
