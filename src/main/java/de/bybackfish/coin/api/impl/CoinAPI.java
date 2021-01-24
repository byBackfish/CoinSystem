package de.bybackfish.coin.api.impl;

import de.bybackfish.coin.api.IBank;
import de.bybackfish.coin.api.ICoin;

import java.util.UUID;

public class CoinAPI implements ICoin {

    private final PlayerFactory playerBankFactory;

    public CoinAPI() {
        this.playerBankFactory = new PlayerFactory();
    }

    @Override
    public IBank getPlayerCoins(UUID uuid) {
        return playerBankFactory.getPlayerBank(uuid);
    }
}
