import chess

def main():
    board = chess.Board()

    print("Welcome to Chess!")
    print(board)

    while not board.is_game_over() and not board.is_stalemate():
        user = input("Enter your move (e.g., e2e4): ")
        move = chess.Move.from_uci(user)
        try:
            board.push(move)
            print(board)
        except ValueError:
            print("Invalid move. Please try again.")

    print("Game Over")

if __name__ == "__main__":
    main()
