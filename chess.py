import pygame
from pygame.locals import *

pygame.init()

screen = pygame.display.set_mode((720, 720))

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (127,127,127)

square_size = 64

board_position = (100, 100)

white_piece_images = ['white_rook.jpg', 'white_knight.jpg', 'white_bishop.jpg', 'white_pawn.jpg', 'white_queen.jpg', 'white_king.jpg']
black_piece_images = ['black_rook.jpg', 'black_knight.jpg', 'black_bishop.jpg', 'black_pawn.jpg', 'black_queen.jpg', 'black_king.jpg']

pieces = {}
for image in white_piece_images:
    pieces['white'] = image
for image in black_piece_images:
    pieces['black'] = image

piece_positions = [[1, 0], [6, 0], [1, 1], [6, 1], [0, 0], [7, 0], [0, 7], [7, 7]]

while True:
    for event in pygame.event.get():
        mouse_pos = pygame.mouse.get_pos()
        if event.type == QUIT:
            pygame.quit()
            exit()
        elif event.type == MOUSEBUTTONDOWN:
            for i in range(len(piece_positions)):
                piece_pos = piece_positions[i]
                piece_rect = pygame.Rect(board_position[0] + piece_pos[1] * square_size,
                                         board_position[1] + piece_pos[0] * square_size,
                                         square_size, square_size)
                if piece_rect.collidepoint(mouse_pos):
                    selected_piece = i
        elif event.type == MOUSEBUTTONUP:
            if selected_piece is not None:
                new_square = ((mouse_pos[1] - board_position[1]) // square_size,
                              (mouse_pos[0] - board_position[0]) // square_size)
                if new_square[0] >= 0 and new_square[0] < 8 and new_square[1] >= 0 and new_square[1] < 8:
                    piece_positions[selected_piece] = new_square

                selected_piece = None
    screen.fill(GRAY)
    for row in range (8):
        for column in range (8):
            if (row+column)%2 == 0:
                color = WHITE
            else:
                color = BLACK
            pygame.draw.rect(screen, color, (board_position[0] + column * square_size,board_position[1] + row * square_size, square_size, square_size))
    
    for item in range (len(white_piece_images)):
        image = pygame.image.load(white_piece_images[item])
        screen.blit(image, (board_position[0] * square_size, board_position[1] * square_size))

    pygame.display.update()
