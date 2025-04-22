			if (wraps[0] != 0) {
				adjustedWrapIndices = new int[wraps.length + 1];
				adjustedWrapIndices[0] = 0;
				System.arraycopy(wraps, 0, adjustedWrapIndices, 1, wraps.length);
			} else {
				adjustedWrapIndices = wraps;
			}
