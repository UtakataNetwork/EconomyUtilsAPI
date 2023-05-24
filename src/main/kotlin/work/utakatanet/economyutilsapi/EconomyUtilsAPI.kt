package work.utakatanet.economyutilsapi

import org.bukkit.plugin.java.JavaPlugin

class EconomyUtilsAPI : JavaPlugin() {
    override fun onEnable() {

        logger.info("EconomyUtilsAPI が有効になりました")

        saveDefaultConfig()

    }
    override fun onDisable() {
        // Plugin shutdown logic
    }
}
