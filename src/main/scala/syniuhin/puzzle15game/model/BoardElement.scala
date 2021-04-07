package syniuhin.puzzle15game.model

sealed trait BoardElement
case class BoardNum(value: Int) extends BoardElement
case object BoardEmpty extends BoardElement
