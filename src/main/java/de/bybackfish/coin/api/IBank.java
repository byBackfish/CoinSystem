package de.bybackfish.coin.api;

import java.util.List;

public interface IBank {

    IPlayerBank getBank(String bank);

    void clearBank(String bank);

    boolean createBank(String bank);

    void deleteBank(String bank);

    void renameBank(String from, String to);

    boolean hasBank(String bank);

    List<String> getBankNames();

}
