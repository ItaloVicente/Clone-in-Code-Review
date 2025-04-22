			int[] points = new int[1024];
			int[] inactive = new int[8];
			int index = 0, inactive_index = 0;
			int radius = cornerSize / 2;
			int circX = bounds.x + radius;
			int circY = onBottom ? bounds.y + bounds.height + 1 - header - radius : bounds.y - 1 + radius;
