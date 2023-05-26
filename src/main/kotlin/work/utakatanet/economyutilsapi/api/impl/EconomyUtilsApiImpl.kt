package work.utakatanet.economyutilsapi.api.impl

import org.bukkit.Bukkit
import work.utakatanet.economyutilsapi.EconomyUtilsAPI
import work.utakatanet.economyutilsapi.api.EconomyUtilsApi
import work.utakatanet.economyutilsapi.api.event.TransactionEvent
import work.utakatanet.economyutilsapi.data.TransactionType
import java.util.*

class EconomyUtilsApiImpl: EconomyUtilsApi {

    private val vaultApiHelper = EconomyUtilsAPI.vaultApiHelper
    private val transactionLogHelper = EconomyUtilsAPI.transactionLogHelper

    override fun depositPlayer(uuid: UUID, amount: Double, reason: String): Boolean {
        val event = TransactionEvent(Bukkit.getOfflinePlayer(uuid), TransactionType.DEPOSIT, amount, reason)

        // キャンセルされたら
        if (event.isCancelled) return false

        // 入金処理
        vaultApiHelper.depositPlayer(uuid, amount)

        // ロギング処理
        return transactionLogHelper.addTransactionLog(uuid, TransactionType.DEPOSIT, amount, reason)

    }

    override fun withdrawPlayer(uuid: UUID, amount: Double, reason: String): Boolean {
        val event = TransactionEvent(Bukkit.getOfflinePlayer(uuid), TransactionType.DEPOSIT, amount, reason)

        // キャンセルされたら
        if (event.isCancelled) return false

        // 出金処理
        vaultApiHelper.withdrawPlayer(uuid, amount)

        // ロギング処理
        return transactionLogHelper.addTransactionLog(uuid, TransactionType.WITHDRAW, amount, reason)

    }

}