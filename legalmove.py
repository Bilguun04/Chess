from board import *
from piece import *

class LegalMove:
    def __init__(self, board):
        self.board = board

    def isLegal(self, piece, initial, final):
        in_row = initial[0]
        in_col = initial[1]
        fin_row = final[0]
        fin_col = final[1]

        if type(piece) == Pawn:
            if piece.iswhite():
                if in_col == fin_col and in_row - fin_row == 1:
                    return True
                elif in_col == fin_col and in_row == 6 and in_row - fin_row == 2:
                    return True
                elif abs(in_col - fin_col) == 1 and in_row - fin_row == 1 and not self.board[fin_row][fin_col].isEmpty() and not self.board[fin_row][fin_col].piece.iswhite():
                    return True
            else:
                if in_col == fin_col and fin_row - in_row == 1:
                    return True
                elif in_col == fin_col and in_row == 1 and fin_row - in_row == 2:
                    return True
                elif abs(in_col - fin_col) == 1 and fin_row - in_row == 1 and not self.board.board[fin_row][fin_col].isEmpty() and self.board.board[fin_row][fin_col].piece.iswhite():
                    return True
        elif type(piece) == Rook:
            if in_row == fin_row and in_col != fin_col:
                if in_col < fin_col:
                    for i in range(in_col + 1, fin_col):
                        if not self.board.board[in_row][i].isEmpty():
                            return False
                    return True
                else:
                    for i in range(fin_col + 1, in_col):
                        if not self.board.board[in_row][i].isEmpty():
                            return False
                    return True
            elif in_col == fin_col and in_row != fin_row:
                if in_row < fin_row:
                    for i in range(in_row + 1, fin_row):
                        if not self.board.board[i][in_col].isEmpty():
                            return False
                    return True
                else:
                    for i in range(fin_row + 1, in_row):
                        if not self.board.board[i][in_col].isEmpty():
                            return False
                    return True
        elif type(piece) == Knight:
            if abs(in_row - fin_row) == 2 and abs(in_col - fin_col) == 1:
                return True
            elif abs(in_row - fin_row) == 1 and abs(in_col - fin_col) == 2:
                return True
        elif type(piece) == Bishop:
            if abs(in_row - fin_row) == abs(in_col - fin_col):
                if in_row < fin_row and in_col < fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row + i][in_col + i].isEmpty():
                            return False
                    return True
                elif in_row < fin_row and in_col > fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row + i][in_col - i].isEmpty():
                            return False
                    return True
                elif in_row > fin_row and in_col < fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row - i][in_col + i].isEmpty():
                            return False
                    return True
                elif in_row > fin_row and in_col > fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row - i][in_col - i].isEmpty():
                            return False
                    return True
        elif type(piece) == Queen:
            if in_row == fin_row and in_col != fin_col:
                if in_col < fin_col:
                    for i in range(in_col + 1, fin_col):
                        if not self.board.board[in_row][i].isEmpty():
                            return False
                    return True
                else:
                    for i in range(fin_col + 1, in_col):
                        if not self.board.board[in_row][i].isEmpty():
                            return False
                    return True
            elif in_col == fin_col and in_row != fin_row:
                if in_row < fin_row:
                    for i in range(in_row + 1, fin_row):
                        if not self.board.board[i][in_col].isEmpty():
                            return False
                    return True
                else:
                    for i in range(fin_row + 1, in_row):
                        if not self.board.board[i][in_col].isEmpty():
                            return False
                    return True
            elif abs(in_row - fin_row) == abs(in_col - fin_col):
                if in_row < fin_row and in_col < fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row + i][in_col + i].isEmpty():
                            return False
                    return True
                elif in_row < fin_row and in_col > fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row + i][in_col - i].isEmpty():
                            return False
                    return True
                elif in_row > fin_row and in_col < fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row - i][in_col + i].isEmpty():
                            return False
                    return True
                elif in_row > fin_row and in_col > fin_col:
                    for i in range(1, abs(in_row - fin_row)):
                        if not self.board.board[in_row - i][in_col - i].isEmpty():
                            return False
                    return True
        elif type(piece) == King:
            if abs(in_row - fin_row) <= 1 and abs(in_col - fin_col) <= 1:
                return True
    
    def generate_legal_moves(self, piece, initial):
        legal_moves = []
        for i in range(8):
            for j in range(8):
                if self.isLegal(piece, initial, [i, j]) and (self.board.board[i][j].isEmpty() or self.board.board[i][j].piece.iswhite() != piece.iswhite()):
                    legal_moves.append([i, j])
        return legal_moves
