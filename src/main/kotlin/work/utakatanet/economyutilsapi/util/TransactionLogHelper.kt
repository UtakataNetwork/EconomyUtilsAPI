package work.utakatanet.economyutilsapi.util

import org.bukkit.Bukkit
import work.utakatanet.economyutilsapi.EconomyUtilsAPI
import work.utakatanet.economyutilsapi.api.event.TransactionEvent
import work.utakatanet.economyutilsapi.data.TransactionLog
import work.utakatanet.economyutilsapi.data.TransactionType
import java.util.UUID

class TransactionLogHelper(private val dbh: DatabaseHelper) {

    private val plugin = EconomyUtilsAPI.plugin

    fun addTransactionLog(uuid: UUID, type: TransactionType, afterMoney: Double, amount: Double, action: String, reason: String): Boolean {

        val event = TransactionEvent(Bukkit.getOfflinePlayer(uuid), type, amount, reason)

        plugin.server.pluginManager.callEvent(event)

        if (event.isCancelled) return false

        return dbh.addTransactionLog(uuid, type, afterMoney, amount, action, reason)
    }

    fun getPlayerTransactionLog(limit: Int, offSet: Int, uuid: UUID): MutableMap<Int, TransactionLog> {
        return dbh.getPlayerTransactionLog(limit, offSet, uuid)
    }

}