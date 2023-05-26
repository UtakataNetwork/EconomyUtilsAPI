package work.utakatanet.economyutilsapi.util

import org.bukkit.Bukkit
import work.utakatanet.economyutilsapi.EconomyUtilsAPI
import work.utakatanet.economyutilsapi.data.TransactionLog
import work.utakatanet.economyutilsapi.data.TransactionType
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.UUID

class DatabaseHelper {

    private val plugin = EconomyUtilsAPI.plugin

    private lateinit var host: String
    private lateinit var port: String
    private lateinit var db: String
    private lateinit var args: String

    private lateinit var user: String
    private lateinit var pass: String

    private val connection: Connection
        private get() {
            var con: Connection? = null
            try {
                con = DriverManager.getConnection("jdbc:mysql://${host}:${port}/${db}", user, pass)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return con!!
        }

    init {
        // DB関連の設定の再読み込み
        reloadSettings()

        // テーブルの存在チェック&作成
        tableCreate()
    }

    fun reloadSettings() {
        val section = plugin.config.getConfigurationSection("database")!!
        host    = section.getString("host")!!
        port    = section.getString("port")!!
        db      = section.getString("db")!!
        args    = section.getString("args")!!

        user    = section.getString("user")!!
        pass    = section.getString("pass")!!
    }


    fun tableCreate() {
        try {
            val con = connection
            val dbm = con.metaData

            val transactionLogTable = dbm.getTables(null, null, "eco_transactionLog", null)

            // テーブルが存在しなければ
            if (!transactionLogTable.next()) {
                val ps = con.prepareStatement("CREATE TABLE `eco_transactionLog` (`id` INT AUTO_INCREMENT, `uuid` VARCHAR(100) NOT NULL DEFAULT '', `type` VARCHAR(50) NOT NULL DEFAULT '', `amount` DOUBLE NOT NULL DEFAULT 0, `action` VARCHAR(500) NOT NULL DEFAULT '', `reason` VARCHAR(1000) NOT NULL DEFAULT '', `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, `updated_at` TIMESTAMP DEFAULT  CURRENT_TIMESTAMP, PRIMARY KEY (`id`));")
                ps.executeUpdate()
            }
        }catch (e: SQLException) {
            plugin.logger.warning("データベースのテーブルのチェック中にエラーが発生しました！")
            e.printStackTrace()
        }
    }


    /**
     * 取引ログを登録します。
     *
     * @param uuid プレイヤーのUUID
     * @param type 取引タイプ(入金, 出金など)
     * @param amount 金額
     * @param action 簡単な理由
     * @param reason 取引の理由
     *
     * @return 正しく登録処理が実行されたか
     */
    fun addTransactionLog(uuid: UUID, type: TransactionType, amount: Double, action: String, reason: String): Boolean {
        try {
            val con = connection

            val psCreate = con.prepareStatement("INSERT INTO `eco_transactionLog` (`uuid`, `type`, `amount`, `action`, `reason`) VALUES (?, ?, ?, ?, ?);")
            psCreate.setString(1, uuid.toString())
            psCreate.setString(2, type.name)
            psCreate.setDouble(3, amount)
            psCreate.setString(4, action)
            psCreate.setString(5, reason)

            psCreate.executeUpdate()

        }catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

        return true
    }


    /**
     * 取引ログをプレイヤー別に取得します。
     *
     * @param limit 何件のログを取得するか(最大50件)
     * @param offSet 何件目からのログを取得するか
     * @param uuid プレイヤーのUUID
     *
     * @return 正しく登録処理が実行されたか
     */
    fun getPlayerTransactionLog(limit: Int, offSet: Int, uuid: UUID): MutableMap<Int, TransactionLog> {

        var limit_ = limit
        if (limit_ > 50) limit_ = 50

        val logs = mutableMapOf<Int, TransactionLog>()

        try {
            val con = connection

            val psSelect = con.prepareStatement("SELECT * FROM `eco_transactionLog` WHERE uuid = ? LIMIT ? OFFSET ?;")
            psSelect.setString(1, uuid.toString())
            psSelect.setInt(2, limit_)
            psSelect.setInt(3, offSet)

            val selectResult = psSelect.executeQuery()

            while (selectResult.next()) {
                val id          = selectResult.getInt("id")
                val type        = TransactionType.valueOf(selectResult.getString("type"))
                val amount      = selectResult.getDouble("amount")
                val reason      = selectResult.getString("reason")
                val createdAt  = selectResult.getTimestamp("created_at")
                val updatedAt   = selectResult.getTimestamp("updated_at")

                val log = TransactionLog(id, Bukkit.getOfflinePlayer(uuid), type, amount, reason, createdAt, updatedAt)

                logs[id] = log
            }

        }catch (e: SQLException) {
            e.printStackTrace()
            return logs
        }

        return logs
    }

}