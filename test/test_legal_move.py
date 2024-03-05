import unittest
from legalmove import *
from board import *

class TestLegalMove(unittest.TestCase):
    def setUp(self):
        self.board = Board()
        self.legalmove = LegalMove(self.board)

    def test_pawn_legal_move(self):
        self.assertEqual(self.legalmove.generate_legal_moves_piece([1, 0]), [[2, 0], [3, 0]])
        self.assertEqual(self.legalmove.generate_legal_moves_piece([6, 0]), [[4, 0], [5, 0]])

        self.board.board[3][0].piece = Pawn(False)
        self.assertEqual(self.legalmove.generate_legal_moves_piece([1, 0]), [[2, 0]])
    
    def test_knight_legal_move(self):
        self.board.empty()
        self.board.board[3][3].piece = Knight(False)
        self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), [[1, 2], [1, 4], [2, 1], [2, 5], [4, 1], [4, 5], [5, 2], [5, 4]])
        
        self.board.board[2][1].piece = Pawn(False)
        self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), [[1, 2], [1, 4], [2, 5], [4, 1], [4, 5], [5, 2], [5, 4]])
    
    def test_bishop_legal_move(self):
        self.board.empty()
        self.board.board[3][3].piece = Bishop(False)
        self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), sorted([[0, 0], [1, 1], [2, 2], [4, 4], [5, 5], [6, 6], [7, 7],
                                                                           [0, 6], [1, 5], [2, 4], [4, 2], [5, 1], [6, 0]]))
        
        self.board.board[2][2].piece = Pawn(False)
        #self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), sorted([[4, 4], [5, 5], [6, 6], [7, 7], [0, 6], [1, 5], [2, 4], [4, 2], [5, 1], [6, 0]]))
        #self.assertEqual(len(self.legalmove.generate_legal_moves_piece([3, 3])), 10)
        print(sorted(self.legalmove.generate_legal_moves_piece([3, 3])))
        self.board.display()

    def test_king_legal_move(self):
        self.board.empty()
        self.board.board[3][3].piece = King(False)
        self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), [[2, 2], [2, 3], [2, 4], [3, 2], [3, 4], [4, 2], [4, 3], [4, 4]])

        self.board.board[2][2].piece = Pawn(False)
        #self.assertEqual(self.legalmove.generate_legal_moves_piece([3, 3]), [[2, 3], [2, 4], [3, 2], [3, 4], [4, 2], [4, 3], [4, 4]])
    
    def test_queen_legal_move(self):
        self.board.empty()
        self.board.board[3][3].piece = Queen(False)
        #self.assertEqual(self.legalmove.generate_legal_moves_piece(self.board.board[3][3].piece, [3, 3]), [[0, 3], [1, 3], [2, 3], [4, 3], [5, 3], [6, 3], [7, 3],
        #                                                                                                   [0, 0], [1, 1], [2, 2], [4, 4], [5, 5], [6, 6], [7, 7],
        #                                                                                                   [0, 6], [1, 5], [2, 4], [4, 2], [5, 1], [6, 0],
        #                                                                                                   [3, 0], [3, 1], [3, 2], [3, 4], [3, 5], [3, 6], [3, 7]])
        #self.assertEqual(len(self.legalmove.generate_legal_moves_piece(self.board.board[3][3].piece, [3, 3])), 23)

        #self.board.board[3][4].piece = Pawn(False)
        print(len(self.legalmove.generate_legal_moves_piece([3, 3])))
        for item in self.legalmove.generate_legal_moves_piece([3, 3]):
            print(item)
        self.board.display()