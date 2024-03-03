import board
import player

def main():
    b = board.Board()
    black = player.Player(False)
    white = player.Player(True)
    running = True

    while running:
        print("Welcome to Chess Game!")
        b.display()
        

if __name__ == "__main__":
    main()