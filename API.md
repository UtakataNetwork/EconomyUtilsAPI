# EconomyUtilsAPI
### depositPlayer メソッド
プレイヤーの口座にお金を入金します。
#### 引数
| 引数     | 型      | 説明         |
|--------|--------|------------|
| uuid   | UUID   | プレイヤーのUUID |
| amount | Double | 入金する金額     |
| action | String | 簡単な理由      |
| reason | String | 入金する理由     |

DEPOSIT(入金) か WITHDRAW(出金) なのかはAPI側が自動でデータベースに記録します。

#### 例
**/payでの入金時**  
アクション: `/pay`  
理由: `振込者: kuripasanda(48e487ab-92e5-41f1-b6de-75e0e4ca75b4)`

**イベント報酬など**  
アクション: `<イベント名>で<(勝利など)>`  
理由: ` `(なし)


### withdraw メソッド
プレイヤーの口座からお金を出金します。
#### 引数
| 引数     | 型      | 説明         |
|--------|--------|------------|
| uuid   | UUID   | プレイヤーのUUID |
| amount | Double | 出金する金額     |
| action | String | 簡単な理由      |
| reason | String | 出金する理由     |

DEPOSIT(入金) か WITHDRAW(出金) なのかはAPI側が自動でデータベースに記録します。

#### 例
**/payでの出金時**  
アクション: `/pay`  
理由: `振込先: kuripasanda(48e487ab-92e5-41f1-b6de-75e0e4ca75b4)`

**ショップでの購入時**  
アクション: `QuickShopで購入`  
理由: `DIAMOND(1個)(ショップID: 918)`
