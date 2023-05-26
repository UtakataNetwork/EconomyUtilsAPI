package work.utakatanet.economyutilsapi.api

import java.util.UUID

interface EconomyUtilsApi {

    /**
     * プレイヤーの口座にお金を入金します
     *
     * @param uuid 入金先のプレイヤーのUUID
     * @param amount 入金する金額
     * @param reason 入金する理由
     *
     * @return お金が入金されたか(キャンセルされた場合にfalseを返却)
     */
    fun depositPlayer(uuid: UUID, amount: Double, reason: String): Boolean


    /**
     * プレイヤーの口座からお金を出金します
     *
     * @param uuid 出金元のプレイヤーのUUID
     * @param amount 出金する金額
     * @param reason 出金する理由
     *
     * @return お金が出金されたか(キャンセルされた場合にfalseを返却)
     */
    fun withdrawPlayer(uuid: UUID, amount: Double, reason: String): Boolean

}