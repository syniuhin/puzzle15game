package syniuhin.puzzle15game.mechanics

sealed trait GameCommand

sealed trait CursorCommand extends GameCommand
case object CursorMoveUp extends CursorCommand
case object CursorMoveDown extends CursorCommand
case object CursorMoveLeft extends CursorCommand
case object CursorMoveRight extends CursorCommand

sealed trait BlockCommand extends GameCommand
case object BlockMoveUp extends BlockCommand
case object BlockMoveDown extends BlockCommand
case object BlockMoveLeft extends BlockCommand
case object BlockMoveRight extends BlockCommand

object GameCommand {
  def prettyString(commands: Seq[GameCommand]): String =
    commands.zipWithIndex.map {
      case (command, index) => s"\t$index: $command"
    } ++ Seq("\tq: Quit") mkString "\n"
}
