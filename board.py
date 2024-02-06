from piece import *
import chess

class Position:
    def __init__(self, x, y, piece = '.'):
        self.x = x
        self.y = y
        self.piece = piece
    
    def __str__(self):
        if not type(self.piece) == str:
            return self.piece.name
        return self.piece
    
    def isEmpty(self):
        if self.piece == '.':
            return True
        return False

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
    
    def convert_board(self):
        piece_mapping = {
            'P': chess.PAWN,
            'N': chess.KNIGHT,
            'B': chess.BISHOP,
            'R': chess.ROOK,
            'Q': chess.QUEEN,
            'K': chess.KING,
            'p': chess.PAWN | chess.BLACK,
            'n': chess.KNIGHT | chess.BLACK,
            'b': chess.BISHOP | chess.BLACK,
            'r': chess.ROOK | chess.BLACK,
            'q': chess.QUEEN | chess.BLACK,
            'k': chess.KING | chess.BLACK,
            '.': None
        }

        for row_idx, row in enumerate(self.board):
            for col_idx, piece_char in enumerate(row):
                piece = piece_mapping.get(piece_char)
                if piece is not None:
                    square = chess.square(col_idx, 7 - row_idx)
                    board.set_piece_at(square, piece)

        return board
    
    def move(self, user):
        in_row = int(user[0])
        in_col = int(user[1])
        fin_row = int(user[2])
        fin_col = int(user[3])
        if self.board[in_row][in_col].piece == '.':
            return False
        if self.board[fin_row][fin_col].piece == '.':
            self.board[in_row][in_col], self.board[fin_row][fin_col] = '.', self.board[in_row][in_col]
            return True
        if self.board[in_row][in_col].piece.white == self.board[fin_row][fin_col].piece.white:
            return False

if __name__ == "__main__":
    board = chess.Board()
    b = Board()
    b.reset_board()
    b.board[2][5] = King(False)
    b.board[0][4] = '.'
    chess_board = b.convert_board()
    print(chess_board)
    b.display()