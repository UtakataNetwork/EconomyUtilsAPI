package work.utakatanet.economyutilsapi

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import work.utakatanet.economyutilsapi.util.VaultApiHelper


class EconomyUtilsAPI : JavaPlugin() {

    companion object {

        lateinit var vaultEconomy: Economy private set

        lateinit var vaultApiHelper: VaultApiHelper private set

    }

    override fun onEnable() {

        logger.info("EconomyUtilsAPI が有効になりました")

        saveDefaultConfig()

        // VaultAPI連携(エコノミー)
        if (!setupEconomy()) {
            logger.warning("VaultAPIとの連携に失敗しました")
            pluginLoader.disablePlugin(this)
        }

        vaultApiHelper = VaultApiHelper()

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
