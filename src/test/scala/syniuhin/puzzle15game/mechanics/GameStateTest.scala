package syniuhin.puzzle15game.mechanics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should._
import syniuhin.puzzle15game.model.{BoardEmpty, BoardNum}

class GameStateTest extends AnyFlatSpec with Matchers {
  "GameState" should "produce correct available commands for cursor in top left" in {
    val gameBoard = GameBoard.initCheat.copy(cursor = (0, 0))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveDown,
      CursorMoveRight
    )
  }

  "GameState" should "produce correct available commands for cursor in top right" in {
    val gameBoard =
      GameBoard.initCheat.copy(cursor = (0, GameBoard.columns - 1))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveDown,
      CursorMoveLeft
    )
  }

  "GameState" should "produce correct available commands for cursor in bottom left" in {
    val gameBoard = GameBoard.initCheat.copy(cursor = (GameBoard.rows - 1, 0))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveUp,
      CursorMoveRight
    )
  }

  "GameState" should "produce correct available commands for cursor in bottom right" in {
    val gameBoard = GameBoard.initCheat
      .copy(cursor = (GameBoard.rows - 1, GameBoard.columns - 1))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveUp,
      CursorMoveLeft,
      BlockMoveLeft
    )
  }

  "GameState" should "allow to move block to the bottom" in {
    val gameBoard = GameBoard.initCheat
      .copy(cursor = (GameBoard.rows - 2, GameBoard.columns - 2))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveDown,
      CursorMoveRight,
      CursorMoveUp,
      CursorMoveLeft,
      BlockMoveDown
    )
  }

  "GameState" should "allow to move block to the right" in {
    val gameBoard = GameBoard.initCheat
      .copy(cursor = (GameBoard.rows - 1, GameBoard.columns - 3))
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveRight,
      CursorMoveUp,
      CursorMoveLeft,
      BlockMoveRight
    )
  }

  "GameState" should "allow to move block to the left" in {
    val values = Seq(BoardNum(1)) ++ Seq(
      BoardEmpty
    ) ++ (2 until GameBoard.boardSize).map(BoardNum)

    val gameBoard = GameBoard
      .init(values)
      .copy(cursor = (0, 2))  
    GameState.availableCommands(gameBoard) should contain theSameElementsAs Seq(
      CursorMoveRight,
      CursorMoveLeft,
      CursorMoveDown,
      BlockMoveLeft
    )
  }

  "GameState" should "quit when asked to" in {
    val gameBoard = GameBoard.init()
    assert(GameState.handleInput("q", gameBoard, Seq())._1 == GameInterrupted)
  }
}
