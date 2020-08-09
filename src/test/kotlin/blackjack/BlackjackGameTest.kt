package blackjack

import blackjack.domain.BlackjackGame
import blackjack.domain.CardDeck
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource

class BlackjackGameTest {

    @DisplayName("플레이 입력값 확인")
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = ["ace:con", "$", "ace,"])
    fun checkPlayersInput(playerNames: String) {
        assertThatThrownBy { BlackjackGame(playerNames, CardDeck()) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @DisplayName("플레이어 수 확인")
    @Test
    fun checkPlayers() {
        assertThat(BlackjackGame("ace,con", CardDeck()).players.size)
            .isEqualTo(2)
    }

    @DisplayName("게임에 사용될 카드 체크")
    @Test
    fun checkCards() {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        assertThat(blackjackGame.getCardCount())
            .isEqualTo(52)
    }

    @DisplayName("첫번째 텀에 플레이어들에게 카드 두개씩 분배하기")
    @Test
    fun startGame() {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        assertThat(blackjackGame.getCurrentPosition())
            .isEqualTo(3)
    }

    @DisplayName("게임 진행여부 입력값 확인하기")
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = ["a", "$"])
    fun checkInputHitOrStay(isHit: String) {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        assertThatThrownBy { blackjackGame.hitOrStay(isHit) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @DisplayName("카드를 추가로 받기")
    @Test
    fun checkHit() {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        blackjackGame.hitOrStay("y")
        assertThat(blackjackGame.currentPlayer.cards.size)
            .isEqualTo(3)
    }

    @DisplayName("카드를 추가로 안받기")
    @Test
    fun checkStay() {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        blackjackGame.hitOrStay("n")
        assertThat(blackjackGame.currentPlayer.cards.size)
            .isEqualTo(2)
    }

    @DisplayName("사용자가 현재 가지고 있는 카드 포인트의 합이 21이 넘는지 안넘는지 확인")
    @Test
    fun checkBust() {
        val blackjackGame = BlackjackGame("ace,con", CardDeck())
        repeat(10) { blackjackGame.hitOrStay("y") }
        assertThat(blackjackGame.checkBust()).isEqualTo(true)
    }
}
