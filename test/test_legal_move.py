import unittest
from legalmove import *
from board import *

class TestLegalMove(unittest.TestCase):
    def test_legal_move_initial(self):
        board = Board()

        r = LegalMove(board)
        legalmove = r.generate_legal_moves(board.board[0][0].piece, [0, 0])
        self.assertEqual(legalmove, [])

        n = LegalMove(board)
        legalmove = n.generate_legal_moves(board.board[0][1].piece, [0, 1])
        self.assertEqual(legalmove, [[2, 0], [2, 2]])

        b = LegalMove(board)
        legalmove = b.generate_legal_moves(board.board[0][2].piece, [0, 2])
        self.assertEqual(legalmove, [])

        q = LegalMove(board)
        legalmove = q.generate_legal_moves(board.board[0][3].piece, [0, 3])
        self.assertEqual(legalmove, [])

        k = LegalMove(board)
        legalmove = k.generate_legal_moves(board.board[0][4].piece, [0, 4])
        self.assertEqual(legalmove, [])

        b = LegalMove(board)
        legalmove = b.generate_legal_moves(board.board[0][5].piece, [0, 5])
        self.assertEqual(legalmove, [])

        n = LegalMove(board)
        legalmove = n.generate_legal_moves(board.board[0][6].piece, [0, 6])
        self.assertEqual(legalmove, [[2, 5], [2, 7]])

        r = LegalMove(board)
        legalmove = r.generate_legal_moves(board.board[0][7].piece, [0, 7])
        self.assertEqual(legalmove, [])

        for col in range (8):
            p = LegalMove(board)
            legalmove = p.generate_legal_moves(board.board[1][col].piece, [1, col])
            self.assertEqual(legalmove, [[2, col], [3, col]])
    
    def test_rook_legal_moves(self):
        board = Board()
        legalmove = LegalMove(board)

        board.empty()
        board.board[0][0] = Rook(True)
        self.assertTrue(legalmove.isLegal(board.board[0][0], [0, 0], [0, 7]))

        board.board[0][4] = Rook(True)
        board.display()

        self.assertFalse(legalmove.isLegal(board.board[0][0], [0, 0], [0, 7]))