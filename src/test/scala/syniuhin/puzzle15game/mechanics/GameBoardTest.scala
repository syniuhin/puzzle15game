package syniuhin.puzzle15game.mechanics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should._
import syniuhin.puzzle15game.model.{BoardEmpty, BoardNum}

class GameBoardTest extends AnyFlatSpec with Matchers {

  "GameBoard" should "have cursor initially set to an empty spot" in {
    val gameBoard = GameBoard.initRandom
    assert(
      gameBoard.boardModel
        .values(gameBoard.cursor._1)(gameBoard.cursor._2) == BoardEmpty
    )
  }

  "GameBoard" should "get randomized correctly" in {
    val gameBoard1 = GameBoard.initRandom
    val gameBoard2 = GameBoard.initRandom
    gameBoard1 should not be gameBoard2
  }

  private val gameBoardFinal = GameBoard.init(
    (1 until GameBoard.boardSize).map(BoardNum) ++ Seq(BoardEmpty)
  )
  "GameBoard" should "indicate when the game is finished" in {
    GameBoard.hasGameFinished(gameBoardFinal) should be(true)
    GameBoard.hasGameFinished(GameBoard.initCheat) should be(false)
  }

  "GameBoard" should "move the block correctly" in {
    val gameBoardCheat = GameBoard.initCheat
    val gameBoard = gameBoardCheat.copy(cursor =
      (gameBoardCheat.cursor._1, gameBoardCheat.cursor._2 + 1)
    )

    // Not worth making GameBoard.moveBlock public, hence calling this function
    GameBoard.applyCommand(gameBoard, BlockMoveLeft) should equal(
      gameBoardFinal
    )
  }
}
