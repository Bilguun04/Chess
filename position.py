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
