import chess
import chess.engine

def print_board(board):
    print(board)

def get_user_move():
    while True:
        try:
            move = input("Enter your move (e.g., e2e4): ")
            return chess.Move.from_uci(move)
        except ValueError:
            print("Invalid move! Please enter a valid move.")

def main():
    board = chess.Board()

    while not board.is_game_over():
        print_board(board)
        move = get_user_move()

        if move in board.legal_moves:
            board.push(move)
        else:
            print("Invalid move! Please enter a legal move.")

    print("Game over.")
    result = board.result()
    if result == "1-0":
        print("White wins!")
    elif result == "0-1":
        print("Black wins!")
    else:
        print("It's a draw!")

if __name__ == "__main__":
    main()