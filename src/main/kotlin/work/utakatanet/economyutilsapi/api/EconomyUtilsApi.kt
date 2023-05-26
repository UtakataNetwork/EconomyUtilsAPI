package work.utakatanet.economyutilsapi.api

import java.math.BigDecimal
import java.util.UUID

interface EconomyUtilsApi {

    /**
     * プレイヤーの口座にお金を入金します
     *
     * @param uuid 入金先のプレイヤーのUUID
     * @param amount 入金する金額
     * @param action 簡単な理由
     * @param reason 入金する理由
     *
     * @return お金が入金されたか(キャンセルされた場合にfalseを返却)
     */
    fun depositPlayer(uuid: UUID, amount: Double, action: String, reason: String): Boolean
    fun depositPlayer(uuid: UUID, amount: BigDecimal, action: String, reason: String): Boolean


    /**
     * プレイヤーの口座からお金を出金します
     *
     * @param uuid 出金元のプレイヤーのUUID
     * @param amount 出金する金額
     * @param action 簡単な理由
     * @param reason 出金する理由
     *
     * @return お金が出金されたか(キャンセルされた場合にfalseを返却)
     */
    fun withdrawPlayer(uuid: UUID, amount: Double, action: String, reason: String): Boolean
    fun withdrawPlayer(uuid: UUID, amount: BigDecimal, action: String, reason: String): Boolean


}