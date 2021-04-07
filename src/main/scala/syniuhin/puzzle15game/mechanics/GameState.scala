package syniuhin.puzzle15game.mechanics

import syniuhin.puzzle15game.model._

import scala.util.{Failure, Success, Try}

sealed trait GameState {
  def message: String
}
case object GameStateInit extends GameState {
  val message = "welcome aboard"
}
case object CursorMoved extends GameState {
  val message = "the cursor has been moved"
}
case object BlockMoved extends GameState {
  val message = "a block has been moved"
}

sealed trait GameFinished extends GameState
case object PuzzleSolved extends GameFinished {
  val message =
    "congrats on completing the puzzle! hope you weren't cheating :)"
}
case object GameInterrupted extends GameFinished {
  val message = "bye!"
}

sealed trait GameStateFailure extends GameState
case class InvalidCommandEntered(message: String) extends GameStateFailure

object GameState {
  def availableCommands(gameBoard: GameBoard): Seq[GameCommand] = {
    val GameBoard(boardModel, cursor) = gameBoard
    val (x, y) = cursor
    val prohibited: Seq[GameCommand] = Seq(
      // Top row
      if (x == 0)
        Some(CursorMoveUp)
      else None,
      // Bottom row
      if (x == GameBoard.rows - 1)
        Some(CursorMoveDown)
      else None,
      // Leftmost column
      if (y == 0)
        Some(CursorMoveLeft)
      else None,
      // Rightmost column
      if (y == GameBoard.columns - 1)
        Some(CursorMoveRight)
      else None,
      // Top row + spot above is not empty
      if (x == 0 || boardModel.values(x - 1)(y) != BoardEmpty)
        Some(BlockMoveUp)
      else None,
      // Bottom row + spot below is not empty
      if (x == GameBoard.rows - 1 || boardModel.values(x + 1)(y) != BoardEmpty)
        Some(BlockMoveDown)
      else None,
      // Leftmost column + spot on the left is not empty
      if (y == 0 || boardModel.values(x)(y - 1) != BoardEmpty)
        Some(BlockMoveLeft)
      else None,
      // Rightmost column + spot on the right is not empty
      if (
        y == GameBoard.columns - 1 || boardModel.values(x)(y + 1) != BoardEmpty
      ) Some(BlockMoveRight)
      else None
    ).flatten

    (Set[GameCommand](
      CursorMoveUp,
      CursorMoveDown,
      CursorMoveLeft,
      CursorMoveRight,
      BlockMoveUp,
      BlockMoveDown,
      BlockMoveLeft,
      BlockMoveRight
    ) -- prohibited).toSeq
  }

  def handleInput(
      input: String,
      gameBoard: GameBoard,
      availableCommands: Seq[GameCommand]
  ): (GameState, GameBoard) = {
    if (input == "q") (GameInterrupted, gameBoard)
    else {
      Try(input.toInt).map(availableCommands).map { command =>
        (command, GameBoard.applyCommand(gameBoard, command))
      } match {
        case Success((_, gameBoardNext))
            if GameBoard.hasGameFinished(gameBoardNext) =>
          (PuzzleSolved, gameBoardNext)

        case Success((_: BlockCommand, gameBoardNext)) =>
          (BlockMoved, gameBoardNext)

        case Success((_: CursorCommand, gameBoardNext)) =>
          (CursorMoved, gameBoardNext)

        case Failure(_: NumberFormatException) =>
          (
            InvalidCommandEntered(
              s"Please enter an integer from 0 to ${availableCommands.length - 1}."
            ),
            gameBoard
          )

        case Failure(e: IndexOutOfBoundsException) =>
          (
            InvalidCommandEntered(
              s"Command is not available: ${e.getMessage}."
            ),
            gameBoard
          )

        case Failure(e) =>
          (
            InvalidCommandEntered(s"$e"),
            gameBoard
          )
      }
    }
  }
}
