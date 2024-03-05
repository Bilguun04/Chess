from board import *
from piece import *

class LegalMove:
    def __init__(self, board):
        self.board = board

    def isLegal(self, initial, final):
        piece = self.board.board[initial[0]][initial[1]].piece
        in_row = initial[0]
        in_col = initial[1]
        fin_row = final[0]
        fin_col = final[1]

        if type(piece) == Pawn:
            if piece.iswhite():
                if in_col == fin_col and in_row - fin_row == 1 and self.board.board[fin_row][fin_col].isEmpty():
                    return True
                elif in_col == fin_col and in_row == 6 and in_row - fin_row == 2 and self.board.board[fin_row][fin_col].isEmpty() and self.board.board[fin_row + 1][fin_col].isEmpty():
                    return True
                elif abs(in_col - fin_col) == 1 and in_row - fin_row == 1 and not self.board.board[fin_row][fin_col].isEmpty() and not self.board.board[fin_row][fin_col].piece.iswhite():
                    return True
            else:
                if in_col == fin_col and fin_row - in_row == 1 and self.board.board[fin_row][fin_col].isEmpty():
                    return True
                elif in_col == fin_col and in_row == 1 and fin_row - in_row == 2 and self.board.board[fin_row][fin_col].isEmpty() and self.board.board[fin_row - 1][fin_col].isEmpty():
                    return True
                elif abs(in_col - fin_col) == 1 and fin_row - in_row == 1 and not self.board.board[fin_row][fin_col].isEmpty() and self.board.board[fin_row][fin_col].piece.iswhite():
                    return True

        elif type(piece) == Knight:
            if abs(in_row - fin_row) == 2 and abs(in_col - fin_col) == 1 and (self.board.board[fin_row][fin_col].isEmpty() or self.board.board[fin_row][fin_col].piece.white != self.board.board[in_row][in_col].piece.white):
                return True
            elif abs(in_row - fin_row) == 1 and abs(in_col - fin_col) == 2 and (self.board.board[fin_row][fin_col].isEmpty() or self.board.board[fin_row][fin_col].piece.white != self.board.board[in_row][in_col].piece.white):
                return True

        elif type(piece) == Bishop:
            directions = [(-1, -1), (-1, 1), (1, -1), (1, 1)]
            for dr, dc in directions:
                for distance in range (8):
                    row, col = in_row + dr*distance, in_col + dc*distance
                    if not (0 <= row < 8 and 0 <= col < 8):
                        break
                    if self.board.board[row][col].isEmpty():
                        if row == fin_row and col == fin_col:
                            return True
                
        elif type(piece) == Rook:
            if in_row == fin_row and in_col != fin_col:
                if in_col < fin_col:
                    for i in range(in_col + 1, fin_col):
                        if not self.board.board[in_row][i].isEmpty() and self.board.board[in_row][i].piece.iswhite() != piece.iswhite():
                            return False
                    return True
                else:
                    for i in range(fin_col + 1, in_col):
                        if not self.board.board[in_row][i].isEmpty() and self.board.board[in_row][i].piece.iswhite() != piece.iswhite():
                            return False
                    return True
            elif in_col == fin_col and in_row != fin_row:
                if in_row < fin_row:
                    for i in range(in_row + 1, fin_row):
                        if not self.board.board[i][in_col].isEmpty() and self.board.board[i][in_col].piece.iswhite() != piece.iswhite():
                            return False
                    return True
                else:
                    for i in range(fin_row + 1, in_row):
                        if not self.board.board[i][in_col].isEmpty() and self.board.board[i][in_col].piece.iswhite() != piece.iswhite():
                            return False
                    return True
            return False

        elif type(piece) == Queen:
            if in_row == fin_row and in_col != fin_col:
                if in_col < fin_col:
                    for col in range (in_col, fin_col+1):
                        if not self.board.board[in_row][col].isEmpty():
                            return False
                else:
                    for col in range (in_col, fin_col-1, -1):
                        if not self.board.board[in_row][col].isEmpty():
                            return False
                return True

        elif type(piece) == King:
            if abs(in_row - fin_row) <= 1 and abs(in_col - fin_col) <= 1:
                return True
    
    def generate_legal_moves_piece(self, initial):
        legal_moves = []
        for i in range(8):
            for j in range(8):
                if self.isLegal(initial, [i, j]):
                    legal_moves.append([i, j])
        legal_moves = sorted(legal_moves)
        return legal_moves

    def generate_legal_move_board(self):
        sol = {}
        for line in self.board.board:
            for position in line:
                if str(position.piece) != '.':
                    legalmoves = self.generate_legal_moves_piece(position.piece, [position.x, position.y])
                    if len(legalmoves) > 0:
                        sol[str(Position(position.x, position.y, position.piece))] = legalmoves
        return sol