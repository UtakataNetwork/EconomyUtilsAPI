package work.utakatanet.economyutilsapi.api.event

import org.bukkit.OfflinePlayer
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import work.utakatanet.economyutilsapi.data.TransactionType

class TransactionEvent(
    val owner: OfflinePlayer,
    val type: TransactionType,
    val amount: Double,
    var reason: String,
): Event(), Cancellable {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList { return handlers }
    }

    override fun getHandlers(): HandlerList { return getHandlerList() }

    private var cancelled = false

    override fun setCancelled(cancel: Boolean) { cancelled = cancel }
    override fun isCancelled(): Boolean { return cancelled }

}