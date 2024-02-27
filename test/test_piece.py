import unittest
from piece import *

class TestPiece(unittest.TestCase):
    
    def test_piece_initial(self):
        p = Pawn(True)
        self.assertEqual(p.white, True)

        r = Rook(False)
        self.assertEqual(r.white, False)
    
    def test_piece_str(self):
        q = Queen(True)
        self.assertEqual(str(q), 'q')

        k = King(False)
        self.assertEqual(str(k), 'k')

    def test_piece_iswhite(self):
        b = Bishop(True)
        self.assertEqual(b.iswhite(), True)

        n = Knight(False)
        self.assertEqual(n.iswhite(), False)