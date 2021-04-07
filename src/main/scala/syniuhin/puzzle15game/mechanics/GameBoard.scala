package syniuhin.puzzle15game.mechanics

import syniuhin.puzzle15game.model._

import scala.util.Random

case class GameBoard(boardModel: BoardModel, cursor: (Int, Int)) {
  def stringify: String =
    boardModel.values.zipWithIndex.map {
      case (row, rowIndex) =>
        row.zipWithIndex.map {
          case (value, columnIndex) if cursor == (rowIndex, columnIndex) =>
            (value, ">")
          case (value, _) => (value, " ")
        } map {
          case (BoardNum(value), prefix) => f"$prefix$value%2d"
          case (BoardEmpty, prefix)      => s"$prefix #"
        } mkString "  "
    } mkString "\n"
}

object GameBoard {
  val rows = 4
  val columns = 4
  val boardSize: Int = rows * columns

  def randomInitValues: Seq[BoardElement] =
    Random.shuffle(Seq(BoardEmpty) ++ (1 until boardSize).map(BoardNum))
  val cheatInitValues: Seq[BoardElement] = {
    (1 until boardSize - 1).map(BoardNum) ++ Seq(BoardEmpty) ++ Seq(
      BoardNum(
        boardSize - 1
      )
    )
  }

  def init(initValues: Seq[BoardElement]): GameBoard = {
    val boardModel = BoardModel(initValues.sliding(columns, rows).toSeq)
    val indexOfEmpty = initValues.indexOf(BoardEmpty)
    val cursor = (indexOfEmpty / rows, indexOfEmpty % columns)
    GameBoard(boardModel, cursor)
  }

  def initRandom: GameBoard = init(randomInitValues)

  def initCheat: GameBoard = init(cheatInitValues)

  def hasGameFinished(gameBoard: GameBoard): Boolean =
    gameBoard.boardModel.values.flatten == (1 until boardSize).map(
      BoardNum
    ) ++ Seq(BoardEmpty)

  /**
    * Expects command to be logically valid (e.g. not CursorMoveLeft if we're in the leftmost column).
    * @return a new instance of GameBoard with the command applied.
    */
  def applyCommand(board: GameBoard, command: GameCommand): GameBoard = {
    val GameBoard(boardModel, (x, y)) = board
    command match {
      case CursorMoveUp    => GameBoard(boardModel, (x - 1, y))
      case CursorMoveDown  => GameBoard(boardModel, (x + 1, y))
      case CursorMoveLeft  => GameBoard(boardModel, (x, y - 1))
      case CursorMoveRight => GameBoard(boardModel, (x, y + 1))
      case BlockMoveUp     => moveBlock(board, (x - 1, y))
      case BlockMoveDown   => moveBlock(board, (x + 1, y))
      case BlockMoveLeft   => moveBlock(board, (x, y - 1))
      case BlockMoveRight  => moveBlock(board, (x, y + 1))
    }
  }

  private def moveBlock(board: GameBoard, to: (Int, Int)): GameBoard = {
    val valuesFlat = board.boardModel.values.flatten
    val fromIndex = board.cursor._1 * rows + board.cursor._2
    val toIndex = to._1 * rows + to._2
    val valuesUpdated = valuesFlat.zipWithIndex.map {
      case (_, index) if index == fromIndex => valuesFlat(toIndex)
      case (_, index) if index == toIndex   => valuesFlat(fromIndex)
      case (element, _)                     => element
    }
    board.copy(boardModel =
      BoardModel(valuesUpdated.sliding(columns, rows).toSeq)
    )
  }
}
