package de.bybackfish.coin.api;

import java.util.List;
import java.util.function.Consumer;

public interface IPlayerBank {

    void transfer(IPlayerBank playerBankCoin, long amount);

    void addCoins(long coins);

    void removeCoins(long coins);

    void getCoins(Consumer<Long> coinRequest);

    long getCoins();

    void setCoins(long coins);

    boolean withdrawCoins(long amount);

    boolean depositCoins(long amount);

    String getName();

    List<String> getHistory();

    void addHistory(String format);


}
