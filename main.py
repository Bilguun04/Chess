import board

def main():
    b = board.Board()
    
    running = True

    while running:
        print('Welcome to chess game')
        b.display()
        if b.move():
            b.display()
            print('Nice move!')
        else:
            print('Invalid move!')

if __name__ == "__main__":
    main()