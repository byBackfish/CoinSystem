package de.bybackfish.coin.api;

import java.util.UUID;

public interface ICoin {

    IBank getPlayerCoins(UUID uuid);


}
