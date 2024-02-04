from board import *
import chess

class Player:
    bot = False
    def __init__(self, white):
        self.white = white
    def __str__(self):
        if self.white:
            return 'Player is playing as white'
        else:
            return 'Player is playing as black'
    