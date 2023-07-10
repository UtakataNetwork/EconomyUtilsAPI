package com.github.kuripasanda.economyutilsapi

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import com.github.kuripasanda.economyutilsapi.api.EconomyUtilsApi
import com.github.kuripasanda.economyutilsapi.api.impl.EconomyUtilsApiImpl

import com.github.kuripasanda.economyutilsapi.util.DatabaseHelper
import com.github.kuripasanda.economyutilsapi.util.TransactionLogHelper
import com.github.kuripasanda.economyutilsapi.util.VaultApiHelper


class EconomyUtilsAPI : JavaPlugin() {

    companion object {

        lateinit var plugin: Plugin private set

        private lateinit var databaseHelper: DatabaseHelper

        lateinit var vaultApiHelper: VaultApiHelper private set
        lateinit var vaultEconomy: Economy private set

        lateinit var transactionLogHelper: TransactionLogHelper private set

        lateinit var api: EconomyUtilsApi private set

    }

    override fun onEnable() {

        plugin = this

        logger.info("EconomyUtilsAPI が有効になりました")

        saveDefaultConfig()

        // VaultAPI連携(エコノミー)
        if (!setupEconomy()) {
            logger.warning("VaultAPIとの連携に失敗しました")
            pluginLoader.disablePlugin(this)
        }
        vaultApiHelper = VaultApiHelper()

        // データベース関連
        databaseHelper = DatabaseHelper()

        // 取引ログ関連
        transactionLogHelper = TransactionLogHelper(databaseHelper)

        // API
        api = EconomyUtilsApiImpl()

    }
    override fun onDisable() {
        // Plugin shutdown logic
    }


    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false
        vaultEconomy = rsp.provider
        return vaultEconomy != null
    }
}
