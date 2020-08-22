package blackjack.domain

class BetMoneyMap(private val betMoneyMap: Map<String, BetMoney> = mapOf()) {
    fun getBetMoney(playerName: String): BetMoney = betMoneyMap.getOrDefault(playerName, BetMoney.from("$MIN_MONEY")!!)
}
