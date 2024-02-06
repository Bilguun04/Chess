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
    
    def __str__(self):
        return super().__str__()

class Bishop(Piece):
    def __init__(self, white):
        super().__init__(white, 'b')
    
    def __str__(self):
        return super().__str__()
    
class Knight(Piece):
    def __init__(self, white):
        super().__init__(white, 'n')
    
    def __str__(self):
        return super().__str__()

class Rook(Piece):
    def __init__(self, white):
        super().__init__(white, 'r')
        
    def __str__(self):
        return super().__str__()

class Pawn(Piece):
    def __init__(self, white):
        super().__init__(white, 'p')
        
    def __str__(self):
        return super().__str__()

if __name__ == "__main__":
    k = King(False)
    print(k)
    print(k.iswhite())
    p = Pawn(False)
    print(type(p))
    print(p.iswhite())