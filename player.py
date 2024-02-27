class Player:
    def __init__(self, white):
        self.white = white        

    def move(self, board, legalmove):
        user = input("Enter move: ")
        move = [[int(user[0]), int(user[1])], [int(user[2]), int(user[3])]]
        legalmoves = legalmove.generate_legal_moves(board.getPositon(move[0][0], move[0][1]).piece, move[0])
        if move[1] in legalmoves:
            return board.move(move)
        return False

    def iswhite(self):
        return self.white

    def __str__(self):
        return 'w' if self.white else 'b'
