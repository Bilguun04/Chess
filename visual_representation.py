import pygame
from main import *
import os

# Set the dimensions of the board
BOARD_SIZE = 640
SQUARE_SIZE = BOARD_SIZE // 8

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (128, 128, 128)

# Load images for the pieces
piece_images = {}
for color in ['w', 'b']:
    for piece_type in ['p', 'r', 'n', 'b', 'q', 'k']:
        piece_images[color + piece_type] = pygame.image.load(
            os.path.join('images', f'{color}{piece_type}.png')
        )

# Function to initialize the Pygame window
def initialize_window():
    pygame.init()
    return pygame.display.set_mode((BOARD_SIZE, BOARD_SIZE))

# Function to draw the chess board
def draw_board(screen):
    for row in range(8):
        for col in range(8):
            color = WHITE if (row + col) % 2 == 0 else GRAY
            pygame.draw.rect(screen, color, (col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE))

# Function to draw the chess pieces
def draw_pieces(screen, board):
    for square in chess.SQUARES:
        piece = board.piece_at(square)
        if piece is not None:
            piece_image = piece_images[piece.symbol()]
            screen.blit(piece_image, pygame.Rect(
                (chess.square_file(square) * SQUARE_SIZE, (7 - chess.square_rank(square)) * SQUARE_SIZE),
                (SQUARE_SIZE, SQUARE_SIZE)
            ))

# Main function to run the game
def run_game():
    screen = initialize_window()
    clock = pygame.time.Clock()
    board = chess.Board()

    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        screen.fill(WHITE)
        draw_board(screen)
        draw_pieces(screen, board)
        pygame.display.flip()
        clock.tick(60)

    pygame.quit()

if __name__ == "__main__":
    run_game()
