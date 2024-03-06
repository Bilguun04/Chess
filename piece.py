from position import *

class Piece:
    def __init__(self, white, name):
        self.white = white
        self.name = name

    def __str__(self):
        return self.name

    def iswhite(self):
        return self.white

class King(Piece):
    def __init__(self, white):
        super().__init__(white, 'k')

    def __str__(self):
        return super().__str__()

class Queen(Piece):
    def __init__(self, white):
        super().__init__(white, 'q')
        self.point = 9
    
    def __str__(self):
        return super().__str__()

class Bishop(Piece):
    def __init__(self, white):
        super().__init__(white, 'b')
        self.point = 3
    
    def __str__(self):
        return super().__str__()
    
class Knight(Piece):
    def __init__(self, white):
        super().__init__(white, 'n')
        self.point = 3
    
    def __str__(self):
        return super().__str__()

class Rook(Piece):
    def __init__(self, white):
        super().__init__(white, 'r')
        self.point = 5
        
    def __str__(self):
        return super().__str__()

class Pawn(Piece):
    def __init__(self, white):
        super().__init__(white, 'p')
        self.point = 1
        
    def __str__(self):
        return super().__str__()
