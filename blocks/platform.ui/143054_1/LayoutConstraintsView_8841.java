		}

		if (width) {
			if (quantizedWidth != ISizeProvider.INFINITE && quantizedWidth != 0) {
				result = Math.min(result + quantizedWidth / 2, availableParallel);
				result = result - (result % quantizedWidth);
			}
			if (minWidth != ISizeProvider.INFINITE) {
				if (result < minWidth) {
