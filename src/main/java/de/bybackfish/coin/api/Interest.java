package de.bybackfish.coin.api;

import api.bybackfish.DB;
import de.bybackfish.coin.CoinSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Interest {


    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private final DB database;
    private final long interestDelay = 600000;

    public Interest() {
        this.database = CoinSystem.getInstance().getDatabase();

        startLoop();
    }

    public boolean giveInterestIfPassed() {

        long last = Long.parseLong(database.selectSingleRow("SELECT * FROM data").get("last_interest"));


        if (last + 1000 <= System.currentTimeMillis()) {
            database.execute("UPDATE player_bank SET coins = ROUND(coins * 1.05)");
            database.update("UPDATE data SET last_interest = ?", System.currentTimeMillis());

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("You received Interest on all your Bank Accounts!");
            }

        }

        return true;

    }

    public void startLoop() {

        service.scheduleWithFixedDelay(() -> {

            try {
                giveInterestIfPassed();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }, 0, 30, TimeUnit.MINUTES);
    }
}
