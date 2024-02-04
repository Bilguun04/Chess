from piece import *

class Position:
    def __init__(self, x, y, piece):
        self.setposition(x, y, piece)
    def getpiece(self):
        return self.piece
    def setpiece(self, p):
        self.piece = p
    def setposition(self, x, y, piece):
        self = Position(x, y, piece)