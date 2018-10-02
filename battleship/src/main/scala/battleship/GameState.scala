package battleship

case class GameState(round: Int, boatsLeft: Int, playerTurn: Int) // Class representing the state of the current game, 
                                                //including the round number and the number of boats still alive