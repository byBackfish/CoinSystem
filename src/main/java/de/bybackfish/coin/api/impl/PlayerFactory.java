package de.bybackfish.coin.api.impl;

import api.bybackfish.DB;
import api.bybackfish.Row;
import de.bybackfish.coin.CoinSystem;
import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.IPlayerBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerFactory {

    private final DB db;

    private final PlayerBankFactory playerBankCoinFactory;

    private final HashMap<UUID, IBank> playerBankBuffer = new HashMap<>();

    public PlayerFactory() {
        this.playerBankCoinFactory = new PlayerBankFactory();
        this.db = CoinSystem.getInstance().getDatabase();
    }

    public IBank factory(UUID uuid) {
        IBank playerBank = new IBank() {
            @Override
            public IPlayerBank getBank(String bank) {
                return playerBankCoinFactory.factor(bank, uuid);
            }

            @Override
            public void clearBank(String bank) {
                if (hasBank(bank)) {
                    db.update("UPDATE player_bank SET coins = 0 WHERE uuid = ? AND bank = ?",
                            uuid, bank);
                }
            }

            @Override
            public boolean createBank(String bank) {
                if (!hasBank(bank)) {
                    if (getBankNames().size() >= 8) return false;
                    db.insert("player_bank", new Row().with("coins", 0).with("uuid", uuid)
                            .with("bank", bank).with("history", "Created the Bank!"));
                    return true;
                }

                return false;
            }

            @Override
            public boolean hasBank(String bank) {
                return null != db
                        .selectSingleRow("SELECT * FROM player_bank WHERE uuid = ? AND bank = ?",
                                uuid, bank);
            }

            @Override
            public void renameBank(String from, String to) {
                if (hasBank(from))
                    db.update("UPDATE player_bank SET bank = ? WHERE uuid = ? AND bank = ?", to, uuid, from);
            }

            @Override
            public void deleteBank(String bank) {
                if (hasBank(bank))
                    db.delete("DELETE FROM player_bank WHERE uuid = ? AND bank = ?", uuid, bank);
            }

            @Override
            public List<String> getBankNames() {
                List<String> bankList = new ArrayList<>();

                List<Row> rows = db.select("SELECT * FROM player_bank WHERE uuid = ?", uuid);
                for (Row row : rows)
                    bankList.add(row.get("bank"));

                return bankList;
            }


        };

        playerBankBuffer.put(uuid, playerBank);
        return playerBank;
    }

    public IBank getPlayerBank(UUID uuid) {

        if (playerBankBuffer.containsKey(uuid))
            return playerBankBuffer.get(uuid);

        return factory(uuid);

    }


}
