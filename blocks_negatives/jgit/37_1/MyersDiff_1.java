			final int snake2y(int snake) {
				return snake / (endA + 1);
			}

			final boolean makeEdit(int snake1, int snake2) {
				int x1 = snake2x(snake1), x2 = snake2x(snake2);
				int y1 = snake2y(snake1), y2 = snake2y(snake2);
