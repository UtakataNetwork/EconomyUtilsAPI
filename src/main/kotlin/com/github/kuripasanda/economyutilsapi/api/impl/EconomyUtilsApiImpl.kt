package com.github.kuripasanda.economyutilsapi.api.impl

import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import com.github.kuripasanda.economyutilsapi.EconomyUtilsAPI
import com.github.kuripasanda.economyutilsapi.api.EconomyUtilsApi
import com.github.kuripasanda.economyutilsapi.api.event.TransactionEvent
import com.github.kuripasanda.economyutilsapi.data.TransactionType
import java.math.BigDecimal
import java.util.*

class EconomyUtilsApiImpl: EconomyUtilsApi {

    private val vaultApiHelper = EconomyUtilsAPI.vaultApiHelper
    private val transactionLogHelper = EconomyUtilsAPI.transactionLogHelper

    /**
     * プレイヤーの口座に入金
     */
    override fun depositPlayer(uuid: UUID, amount: Double, action: String, reason: String): Boolean {
        val event = TransactionEvent(Bukkit.getOfflinePlayer(uuid), TransactionType.DEPOSIT, amount, reason)
        var afterMoney: Double
        lateinit var vaultReturn: EconomyResponse

        try {
            // キャンセルされたら
            if (event.isCancelled) return false

            // 入金処理
            vaultReturn = vaultApiHelper.depositPlayer(uuid, amount)
            afterMoney = vaultApiHelper.getPlayerBalance(uuid)
        }catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        // 成功/失敗判定
        if (!vaultReturn.transactionSuccess()) {
            return false
        }

        // ロギング処理
        return transactionLogHelper.addTransactionLog(uuid, TransactionType.DEPOSIT, afterMoney, amount, action, reason)
    }
    override fun depositPlayer(uuid: UUID, amount: BigDecimal, action: String, reason: String): Boolean {
        return depositPlayer(uuid, amount.toDouble(), action, reason)
    }


    /**
     * プレイヤーの口座から出金
     */
    override fun withdrawPlayer(uuid: UUID, amount: Double, action: String, reason: String): Boolean {
        val event = TransactionEvent(Bukkit.getOfflinePlayer(uuid), TransactionType.DEPOSIT, amount, reason)
        var afterMoney: Double
        lateinit var vaultReturn: EconomyResponse

        try {
            // キャンセルされたら
            if (event.isCancelled) return false

            // 出金処理
            vaultReturn = vaultApiHelper.withdrawPlayer(uuid, amount)
            afterMoney = vaultApiHelper.getPlayerBalance(uuid)
        }catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        // 成功/失敗判定
        if (!vaultReturn.transactionSuccess()) {
            return false
        }

        // ロギング処理
        return transactionLogHelper.addTransactionLog(uuid, TransactionType.WITHDRAW, afterMoney, amount, action, reason)

    }
    override fun withdrawPlayer(uuid: UUID, amount: BigDecimal, action: String, reason: String): Boolean {
        return withdrawPlayer(uuid, amount.toDouble(), action, reason)
    }


    /**
     * プレイヤーの残高を取得
     */
    override fun getBalance(uuid: UUID): Double {
        return vaultApiHelper.getPlayerBalance(uuid)
    }


    /**
     * プレイヤーの残高があるのか確認
     */
    override fun hasMoney(uuid: UUID): Boolean {
        return vaultApiHelper.hasMoney(uuid)
    }


    /**
     * 指定した金額以上の残高があるか確認
     */
    override fun hasMoney(uuid: UUID, amount: Double): Boolean {
        return vaultApiHelper.hasMoney(uuid, amount)
    }

    override fun hasMoney(uuid: UUID, amount: BigDecimal): Boolean {
        return hasMoney(uuid, amount.toDouble())
    }

}