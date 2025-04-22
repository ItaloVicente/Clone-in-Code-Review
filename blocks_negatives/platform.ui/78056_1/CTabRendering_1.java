			if (!onBottom) {
				int[] ltt = drawCircle(leftIndex, circY, radius, LEFT_TOP);
				for (int i = 0; i < ltt.length / 2; i += 2) {
					int tmp = ltt[i];
					ltt[i] = ltt[ltt.length - i - 2];
					ltt[ltt.length - i - 2] = tmp;
					tmp = ltt[i + 1];
					ltt[i + 1] = ltt[ltt.length - i - 1];
					ltt[ltt.length - i - 1] = tmp;
				}
				System.arraycopy(ltt, 0, points, index, ltt.length);
				index += ltt.length;
