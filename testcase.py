from piece import *
from board import *
from main import *

def piecetest():
    k = King(False)
    assert str(k) == 'k', 'This is king!'
    assert k.iswhite() is False, 'This is black king!'

    q = Queen(True)
    assert str(q) == 'q', 'This is queen!'
    assert q.iswhite() is True, 'This is white queen!'

    b = Bishop(False)
    assert str(b) == 'b', 'This is bishop!'
    assert b.iswhite() is False, 'This is black bishop!'

    n = Knight(True)
    assert str(n) == 'n', 'This is knight!'
    assert n.iswhite() is True, 'This is white knight!'

    r = Rook(False)
    assert str(r) == 'r', 'This is rook!'
    assert r.iswhite() is False, 'This is black rook!'

    p = Pawn(True)
    assert str(p) == 'p', 'This is pawn!'
    assert p.iswhite() is True, 'This is white pawn!'

def boardtest():
    b = Board()
    assert b.board[0][0].piece.name == 'r' and b.board[0][0].piece.white is False, 'This position is occupied by black rook!'
    assert b.board[1][3].piece.name == 'p' and b.board[1][3].piece.white is False, 'This position is occupied by black pawn!'
    assert b.board[7][1].piece.name == 'n' and b.board[7][1].piece.white is True, 'This position is occupied by white knight!'
    assert b.board[7][4].piece.name == 'k' and b.board[7][4].piece.white is True, 'This position is occupied by white king!'
    assert b.board[4][6].piece == '.', 'This is empty space!'
    assert b.board[2][7].piece == '.', 'This is empty space!'

    statement1 = b.move('6050')
    assert statement1 is True and b.board[5][0].piece.name == 'p'
    assert b.board[6][0] == '.'

def maintest():
    

if __name__ == "__main__":
    piecetest()
    boardtest()
    print('All test cases passed!')