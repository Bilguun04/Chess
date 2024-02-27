from piece import *
from position import *
from legalmove import *

class Board:
    def __init__(self):
        self.board = [[None for i in range (8)] for i in range (8)]
        for row in range (8):
            for column in range (8):
                self.board[row][column] = Position(row, column)
        self.reset_board()

    def display(self):
        for row in self.board:
            print(' '.join(map(str, row)))
    
    def getPosition(self, x, y):
        return self.board[x][y]

    def reset_board(self):
        for i, j in [[0, False], [7, True]]:
            self.board[i][0] = Position(0, 0, Rook(j))
            self.board[i][1] = Position(0, 1, Knight(j))
            self.board[i][2] = Position(0, 2, Bishop(j))
            self.board[i][3] = Position(0, 3, Queen(j))
            self.board[i][4] = Position(0, 4, King(j))
            self.board[i][5] = Position(0, 5, Bishop(j))
            self.board[i][6] = Position(0, 6, Knight(j))
            self.board[i][7] = Position(0, 7, Rook(j))
        for i in range (8):
            self.board[1][i] = Position(1, i, Pawn(False))
            self.board[6][i] = Position(6, i, Pawn(True))
    
    def move(self, user):
        in_row = int(user[0][0])
        in_col = int(user[0][1])
        fin_row = int(user[1][0])
        fin_col = int(user[1][1])

        if type(self.board[in_row][in_col].piece) == str:
            return False
        if str(self.board[fin_row][fin_col]) == '.':
            self.board[fin_row][fin_col].piece = self.board[in_row][in_col].piece
            self.board[in_row][in_col].piece = '.'
            return True
        if self.board[in_row][in_col].piece.iswhite() != self.board[fin_row][fin_col].piece.iswhite():
            self.board[fin_row][fin_col].piece = self.board[in_row][in_col].piece
            self.board[in_row][in_col].piece = '.'
            return True
        return False

if __name__ == "__main__":
    b = Board()
    print(b.move(['01', '20']))
    b.display()
