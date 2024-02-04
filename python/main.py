import chess
import chessdotcom

board = chess.Board()

running = False
def check_move(move, legal_moves):
    if move not in legal_moves:
        move = input('Enter your move again: ')
        check_move(move, legal_moves)
    return True

if __name__ == "__main__":
    while running:
        legal_moves = board.legal_moves
        move = input('Enter your move: ')
        if not check_move(move, legal_moves):
            check_move(move, legal_moves)
        