from position import *
from board import *

class Piece:
    killed = False
    white = False
    def __init__(self, white):
        self.white = white
    def iswhite(self):
        return self.white
    def iskilled(self):
        return self.killed
    def setkilled(self):
        self.killed = True

class King(Piece):
    def __init__(self, white):
        super().__init__(white)

class Queen(Piece):
    def __init__(self, white):
        super().__init__(white)

class Bishop(Piece):
    def __init__(self, white):
        super().__init__(white)

class Knight(Piece):
    def __init__(self, white):
        super().__init__(white)

class Rook(Piece):
    def __init__(self, white):
        super().__init__(white)

class Pawn(Piece):
    def __init__(self, white):
        super().__init__(white)