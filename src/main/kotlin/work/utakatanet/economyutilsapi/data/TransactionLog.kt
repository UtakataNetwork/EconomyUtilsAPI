package work.utakatanet.economyutilsapi.data

import org.bukkit.OfflinePlayer
import java.sql.Timestamp

class TransactionLog(
    val id: Int,
    val owner: OfflinePlayer,
    var type: TransactionType,
    var amount: Double,
    var reason: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
) {

}