package work.utakatanet.economyutilsapi.util

import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import work.utakatanet.economyutilsapi.EconomyUtilsAPI
import java.util.UUID

class VaultApiHelper {

    private val vaultEconomy = EconomyUtilsAPI.vaultEconomy

    /**
     * 入金
     */
    fun depositPlayer(uuid: UUID, amount: Double): EconomyResponse {

        val player = Bukkit.getOfflinePlayer(uuid)

        // アカウントがなければ登録
        if (!vaultEconomy.hasAccount(player)) vaultEconomy.createPlayerAccount(player)

        return vaultEconomy.depositPlayer(player, amount)

    }

    /**
     * 出金
     */
    fun withdrawPlayer(uuid: UUID, amount: Double): EconomyResponse {

        val player = Bukkit.getOfflinePlayer(uuid)

        // アカウントがなければ登録
        if (!vaultEconomy.hasAccount(player)) vaultEconomy.createPlayerAccount(player)

        return vaultEconomy.withdrawPlayer(player, amount)

    }

    /**
     * 所持金取得
     */
    fun getPlayerBalance(uuid: UUID): Double {
        val player = Bukkit.getOfflinePlayer(uuid)
        return vaultEconomy.getBalance(player)
    }

    /**
     *  お金を持っているか
     */
    fun hasMoney(uuid: UUID): Boolean {
        return getPlayerBalance(uuid) > 0.0
    }

    /**
     * 特定の金額のお金を持っているか
     */
    fun hasMoney(uuid: UUID, amount: Double): Boolean {
        return getPlayerBalance(uuid) >= amount
    }

}