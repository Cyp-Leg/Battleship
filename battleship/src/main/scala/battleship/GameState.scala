package battleship

case class GameState(round: Int, playerTurn: Int, p1wins: Int, p2wins: Int, startingPlayer: Int, gamesNb: Int) // Class representing the state of the current game, 
                                                //including the round number and the number of boats still alive