# EconomyUtilsAPI
対応バージョン: [1.0.2](https://github.com/UtakataNetwork/EconomyUtilsAPI/releases/tag/v1.0.2)

## APIを取得する
API取得の例です。  
メインクラスにコードを書く場合は、onEnableメソッドの中などに書いてください。
### Java
```java
public static final EconomyUtilsApi ecoApi = EconomyUtilsAPI.api;

// または
public static EconomyUtilsApi ecoApi = null;

@Override
public void onEnable() {
  ecoApi = EconomyUtilsAPI.api;
}
```
### Kotlin
```kotlin
val ecoApi = EconomyUtilsAPI.api

// または
lateinit var ecoApi: EconomyUtilsApi private set

override fun onEnable() {
  ecoApi = EconomyUtilsAPI.api
}
```

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

### getBalance メソッド
プレイヤーの所持金を取得します。

#### 引数
| 引数     | 型      | 説明         |
|--------|--------|------------|
| uuid   | UUID   | プレイヤーのUUID |

#### 返り値
Double: プレイヤーの所持金

### hasMoney(UUID) メソッド
プレイヤーがお金を持っているかを取得します

#### 引数
| 引数     | 型      | 説明         |
|--------|--------|------------|
| uuid   | UUID   | プレイヤーのUUID |

#### 返り値
true → 持っている  
false → 持っていない
