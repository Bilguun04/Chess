import unittest
from board import *

class TestBoard(unittest.TestCase):

    def test_board_size(self):
        b = Board()
        self.assertEqual(len(b.board), 8)
        for line in b.board:
            self.assertEqual(len(line), 8)

    def test_board_pieces(self):
        b = Board()
        self.assertEqual(str(b.board[0][0]), 'r')
        self.assertEqual(str(b.board[0][1]), 'n')
        self.assertEqual(str(b.board[0][2]), 'b')
        self.assertEqual(str(b.board[0][3]), 'q')
        self.assertEqual(str(b.board[0][4]), 'k')
        self.assertEqual(str(b.board[0][5]), 'b')
        self.assertEqual(str(b.board[0][6]), 'n')
        self.assertEqual(str(b.board[0][7]), 'r')
        for item in range (8):
            self.assertEqual(str(b.board[1][item]), 'p')

    def test_board__display(self):
        b = Board()
        b.display()

    def test_board__reset_board(self):
        b = Board()
        b.reset_board()
        self.assertEqual(str(b.board[0][0]), 'r')
        self.assertEqual(str(b.board[0][1]), 'n')
        self.assertEqual(str(b.board[0][2]), 'b')
        self.assertEqual(str(b.board[0][3]), 'q')
        self.assertEqual(str(b.board[0][4]), 'k')
        self.assertEqual(str(b.board[0][5]), 'b')
        self.assertEqual(str(b.board[0][6]), 'n')
        self.assertEqual(str(b.board[0][7]), 'r')
        for item in range (8):
            self.assertEqual(str(b.board[1][item]), 'p')

    def test_board_move(self):
        b = Board()
        self.assertEqual(b.move(['10', '20']), True)
        self.assertEqual(str(b.board[1][0]), '.')
        self.assertEqual(str(b.board[2][0]), 'p')
        
        self.assertEqual(b.move(['07', '17']), False)
        self.assertEqual(str(b.board[0][7]), 'r')
        self.assertEqual(str(b.board[1][7]), 'p')

        self.assertEqual(b.move(['01', '22']), True)
        self.assertEqual(str(b.board[0][1]), '.')
        self.assertEqual(str(b.board[2][2]), 'n')

        self.assertEqual(b.move(['60', '03']), True)
        self.assertEqual(str(b.board[6][0]), '.')
        self.assertEqual(str(b.board[0][3]), 'p')