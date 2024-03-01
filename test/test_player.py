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