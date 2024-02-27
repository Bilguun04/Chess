import unittest
from unittest.mock import patch
from player import Player

class TestPlayer(unittest.TestCase):
    def setUp(self):
        self.white_player = Player(white=True)
        self.black_player = Player(white=False)

    def test_iswhite(self):
        self.assertTrue(self.white_player.iswhite())
        self.assertFalse(self.black_player.iswhite())

    @patch('builtins.input', return_value="1122")
    def test_move_valid(self, mock_input):
        class MockBoard:
            def getPositon(self, x, y):
                return self

            def move(self, move):
                return True

        class MockLegalMove:
            def generate_legal_moves(self, piece, position):
                return [[1, 2], [2, 2]]  

        board = MockBoard()
        legalmove = MockLegalMove()

        self.assertTrue(self.white_player.move(board, legalmove))

        self.assertFalse(self.black_player.move(board, legalmove))

    @patch('builtins.input', return_value="1122")
    def test_move_invalid(self, mock_input):

        class MockBoard:
            def getPositon(self, x, y):
                return self

            def move(self, move):
                return False

        class MockLegalMove:
            def generate_legal_moves(self, piece, position):
                return [[1, 2], [2, 2]]

        board = MockBoard()
        legalmove = MockLegalMove()

        self.assertFalse(self.white_player.move(board, legalmove))

        self.assertFalse(self.black_player.move(board, legalmove))

if __name__ == '__main__':
    unittest.main()
