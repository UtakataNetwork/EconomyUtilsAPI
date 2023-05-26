package work.utakatanet.economyutilsapi.util

import org.bukkit.Bukkit
import work.utakatanet.economyutilsapi.EconomyUtilsAPI
import java.util.UUID

class VaultApiHelper {

    private val vaultEconomy = EconomyUtilsAPI.vaultEconomy

    fun depositPlayer(uuid: UUID, amount: Double) {

        val player = Bukkit.getOfflinePlayer(uuid)

        // アカウントがなければ登録
        if (!vaultEconomy.hasAccount(player)) vaultEconomy.createPlayerAccount(player)

        vaultEconomy.depositPlayer(player, amount)

    }

    fun withdrawPlayer(uuid: UUID, amount: Double) {

        val player = Bukkit.getOfflinePlayer(uuid)

        // アカウントがなければ登録
        if (!vaultEconomy.hasAccount(player)) vaultEconomy.createPlayerAccount(player)

        vaultEconomy.withdrawPlayer(player, amount)

    }

    fun getPlayerBalance(uuid: UUID): Double {
        val player = Bukkit.getOfflinePlayer(uuid)
        return vaultEconomy.getBalance(player)
    }

    fun hasMoney(uuid: UUID): Boolean {
        return getPlayerBalance(uuid) > 0.0
    }

    fun hasMoney(uuid: UUID, amount: Double): Boolean {
        return getPlayerBalance(uuid) >= amount
    }

}