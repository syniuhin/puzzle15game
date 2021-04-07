package syniuhin.puzzle15game

import syniuhin.puzzle15game.mechanics._

import scala.io.StdIn.readLine

object Puzzle15Game extends App {
  print(
    "Hola!\n\tThis is Puzzle15 game.\n\tPlease enter 'cheat' if you want an easy mode [otherwise enter whatever]: "
  )
  val maybeCheat = readLine()
  var gameBoard =
    if (maybeCheat == "cheat") GameBoard.initCheat else GameBoard.initRandom
  println(gameBoard.stringify)
  var gameState: GameState = GameStateInit
  do {
    val availableCommands = GameState.availableCommands(gameBoard)
    println(GameCommand.prettyString(availableCommands))
    do {
      val commandInput = readLine()
      val (nextState, nextBoard) =
        GameState.handleInput(commandInput, gameBoard, availableCommands)
      gameState = nextState
      gameBoard = nextBoard
      println(gameState.message)
    } while (gameState.isInstanceOf[GameStateFailure])
    println(gameBoard.stringify)
  } while (!gameState.isInstanceOf[GameFinished])
}
