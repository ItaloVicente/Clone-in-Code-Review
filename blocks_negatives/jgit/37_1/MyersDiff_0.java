			abstract boolean meets(int d, int k, int x, int snake);

			final int newSnake(int k, int x) {
				int y = k + x;
				return x + (endA + 1) * y;
			}
