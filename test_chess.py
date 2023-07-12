import pygame
from pygame.locals import *

# Initialize Pygame
pygame.init()

# Set screen size
screen = pygame.display.set_mode((720, 720))

# Set colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
BROWN = (139, 69, 19)

# Set square size
square_size = 64

# Set board position
board_position = (75, 75)

# Set piece positions
piece_positions = [[1, 0], [6, 0], [1, 1], [6, 1], [0, 0], [7, 0], [0, 7], [7, 7]]

# Set piece images
white_piece_images = ['white_rook.jpg', 'white_knight.jpg', 'white_bishop.jpg', 'white_pawn.jpg', 'white_queen.jpg', 'white_king.jpg']
black_piece_images = ['black_rook.jpg', 'black_knight.jpg', 'black_bishop.jpg', 'black_pawn.jpg', 'black_queen.jpg', 'black_king.jpg']

# Load piece images
pieces = []
for image in white_piece_images:
    pieces.append(pygame.image.load(image))
for image in black_piece_images:
    pieces.append(image)

# Set selected piece
selected_piece = None

# Run game loop
while True:
    # Handle events
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            exit()
        elif event.type == MOUSEBUTTONDOWN:
            # Get mouse position
            mouse_pos = pygame.mouse.get_pos()

            # Check if a piece was clicked
            for i in range(len(piece_positions)):
                piece_pos = piece_positions[i]
                piece_rect = pygame.Rect(board_position[0] + piece_pos[1] * square_size,
                                         board_position[1] + piece_pos[0] * square_size,
                                         square_size, square_size)
                if piece_rect.collidepoint(mouse_pos):
                    selected_piece = i

        elif event.type == MOUSEBUTTONUP:
            # Get mouse position
            mouse_pos = pygame.mouse.get_pos()

            # Check if a valid move was made
            if selected_piece is not None:
                new_square = ((mouse_pos[1] - board_position[1]) // square_size,
                              (mouse_pos[0] - board_position[0]) // square_size)
                if new_square[0] >= 0 and new_square[0] < 8 and new_square[1] >= 0 and new_square[1] < 8:
                    piece_positions[selected_piece] = new_square

                selected_piece = None

    # Draw background
    screen.fill(BROWN)

    # Draw board
    for row in range(8):
        for col in range(8):
            if (row + col) % 2 == 0:
                color = WHITE
            else:
                color = BLACK

            pygame.draw.rect(screen, color,
                             (board_position[0] + col * square_size,
                              board_position[1] + row * square_size,
                              square_size, square_size))
    
    for i in range (len(white_piece_images)):
        image = pygame.image.load(white_piece_images[i])
        screen.blit(image, (board_position[0] * square_size, board_position[1] * square_size))

    # Update display
    pygame.display.update()