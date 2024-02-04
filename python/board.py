from position import *
from piece import *
import chess

class Board:
    def __init__(self):
        self.board = [[None for i in range (8)] for i in range (8)]
        self.chess_board = chess.Board()
        self.resetBoard()

    def resetBoard(self):
        self.board[0][0] = Position(0, 0, Rook(False))
        self.board[0][1] = Position(0, 1, Knight(False))
        self.board[0][2] = Position(0, 2, Bishop(False))
        self.board[0][3] = Position(0, 3, Queen(False))
        self.board[0][4] = Position(0, 4, King(False))
        self.board[0][5] = Position(0, 5, Bishop(False))
        self.board[0][6] = Position(0, 6, Knight(False))
        self.board[0][7] = Position(0, 7, Rook(False))

        self.board[7][0] = Position(7, 0, Rook(True))
        self.board[7][1] = Position(7, 1, Knight(True))
        self.board[7][2] = Position(7, 2, Bishop(True))
        self.board[7][3] = Position(7, 3, Queen(True))
        self.board[7][4] = Position(7, 4, King(True))
        self.board[7][5] = Position(7, 5, Bishop(True))
        self.board[7][6] = Position(7, 6, Knight(True))
        self.board[7][7] = Position(7, 7, Rook(True))

        for item in range (8):
            self.board[1][item] = Position(1, item, Pawn(False))
            self.board[6][item] = Position(6, item, Pawn(True))

    def update_to_chess(self):
        print(self.chess_board)

    def display(self):
        for line in range (self.board):
            print(line)

    def generate_legal_moves(self):
        board = chess.Board()

board = Board()
board.update_to_chess()