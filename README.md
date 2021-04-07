# puzzle 15 game

classic sbt application, should work by importing as sbt project in intellij

might've missed some tests, since the time constraint was tight and I had other errands

# Mechanics

console-based interface, example flow:
```
Hola!
	This is Puzzle15 game.
	Please enter 'cheat' if you want an easy mode [otherwise enter whatever]: fair
  1   13    7   14
  3   15    5    2
  9   10    8    4
> #   12    6   11
	0: CursorMoveRight
	1: CursorMoveUp
	q: Quit
1
the cursor has been moved
  1   13    7   14
  3   15    5    2
> 9   10    8    4
  #   12    6   11
	0: CursorMoveRight
	1: CursorMoveUp
	2: CursorMoveDown
	3: BlockMoveDown
	q: Quit
3
a block has been moved
  1   13    7   14
  3   15    5    2
> #   10    8    4
  9   12    6   11
	0: CursorMoveRight
	1: CursorMoveUp
	2: CursorMoveDown
	q: Quit
```

Also, you can use `cheat` mode, which brings you to almost-finished state, in order to test the completion phase:
```
Hola!
	This is Puzzle15 game.
	Please enter 'cheat' if you want an easy mode [otherwise enter whatever]: cheat
  1    2    3    4
  5    6    7    8
  9   10   11   12
 13   14  > #   15
	0: CursorMoveLeft
	1: CursorMoveRight
	2: CursorMoveUp
	q: Quit
```

Have fun!
