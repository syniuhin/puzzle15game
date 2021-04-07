package syniuhin.puzzle15game.mechanics

import org.scalatest.flatspec.AnyFlatSpec
import syniuhin.puzzle15game.model.{BoardEmpty, BoardNum}

class GameBoardTest extends AnyFlatSpec {
  "GameBoard" should "get randomized correctly" in {
    val gameBoard1 = GameBoard.init()
    val gameBoard2 = GameBoard.init()
    assert(gameBoard1 != gameBoard2)
  }

  val gameBoardFinal = GameBoard.init(
    (1 until GameBoard.boardSize).map(BoardNum) ++ Seq(BoardEmpty)
  )
  "GameBoard" should "indicate when the game is finished" in {
    assert(GameBoard.hasGameFinished(gameBoardFinal))
    assert(!GameBoard.hasGameFinished(GameBoard.initCheat))
  }

  "GameBoard" should "move the block correctly" in {
    val gameBoardCheat = GameBoard.initCheat
    val gameBoard = gameBoardCheat.copy(cursor =
      (gameBoardCheat.cursor._1, gameBoardCheat.cursor._2 + 1)
    )

    assert(
      // Not worth making GameBoard.moveBlock public, hence calling this function
      GameBoard.applyCommand(gameBoard, BlockMoveLeft)
        == gameBoardFinal
    )
  }
}
