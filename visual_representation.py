import pygame
from main import *
import chess
import os

BOARD_SIZE = 640
SQUARE_SIZE = BOARD_SIZE // 8

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (128, 128, 128)

piece_images = {}
for color in ['w', 'b']:
    for piece_type in ['p', 'r', 'n', 'b', 'q', 'k']:
        piece_images[color + piece_type] = pygame.image.load(
            os.path.join('images', f'{color}{piece_type}.png')
        )

def initialize_window():
    pygame.init()
    return pygame.display.set_mode((BOARD_SIZE, BOARD_SIZE))

def draw_board(screen):
    for row in range(8):
        for col in range(8):
            color = WHITE if (row + col) % 2 == 0 else GRAY
            pygame.draw.rect(screen, color, (col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE))

def draw_pieces(screen, board):
    for square in chess.SQUARES:
        piece = board.piece_at(square)
        if piece is not None:
            if piece.symbol() == piece.symbol().lower():
                piece_image = piece_images['b' + piece.symbol()]
                screen.blit(piece_image, pygame.Rect(
                    (chess.square_file(square) * SQUARE_SIZE, (7 - chess.square_rank(square)) * SQUARE_SIZE),
                    (SQUARE_SIZE, SQUARE_SIZE)
                ))
            else:
                piece_image = piece_images['w' + piece.symbol().lower()]
                screen.blit(piece_image, pygame.Rect(
                    (chess.square_file(square) * SQUARE_SIZE, (7 - chess.square_rank(square)) * SQUARE_SIZE),
                    (SQUARE_SIZE, SQUARE_SIZE)
                ))

def run_game():
    screen = initialize_window()
    clock = pygame.time.Clock()
    board = chess.Board()

    selected_piece = None
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.MOUSEBUTTONDOWN:
                x, y = pygame.mouse.get_pos()
                file = x // SQUARE_SIZE
                rank = 7 - y // SQUARE_SIZE
                square = chess.square(file, rank)
                if selected_piece is None:
                    selected_piece = square
                else:
                    move = chess.Move(selected_piece, square)
                    if move in board.legal_moves:
                        board.push(move)
                    selected_piece = None

        screen.fill(WHITE)
        draw_board(screen)
        draw_pieces(screen, board)
        pygame.display.flip()
        clock.tick(60)

    pygame.quit()

if __name__ == "__main__":
    run_game()