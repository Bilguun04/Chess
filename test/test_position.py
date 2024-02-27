import unittest
from position import *
from piece import *

class TestPosition(unittest.TestCase):

    def test_position_initial(self):
        p = Position(3, 3)
        self.assertEqual(p.x, 3)
        self.assertEqual(p.y, 3)
        self.assertEqual(p.piece, '.')

    def test_place_piece(self):
        p = Position(3, 3, Rook(True))
        self.assertEqual(str(p.piece), 'r')
        self.assertEqual(p.isEmpty(), False)

    def test_place_empty(self):
        p = Position(3, 3)
        self.assertEqual(str(p.piece), '.')
        self.assertEqual(p.isEmpty(), True)