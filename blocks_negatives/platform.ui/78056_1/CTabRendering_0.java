		int startX = -1, endX = -1;
		if (!onBottom) {
			int[] ltt = drawCircle(circX, circY, radius, LEFT_TOP);
			startX = ltt[6];
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

			int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, RIGHT_TOP);
			endX = rt[rt.length - 4];
			for (int i = 0; i < rt.length / 2; i += 2) {
				int tmp = rt[i];
				rt[i] = rt[rt.length - i - 2];
				rt[rt.length - i - 2] = tmp;
				tmp = rt[i + 1];
				rt[i + 1] = rt[rt.length - i - 1];
				rt[rt.length - i - 1] = tmp;
			}
			System.arraycopy(rt, 0, points, index, rt.length);
			index += rt.length;

			points[index++] = selectionX2 = bounds.width + circX - radius;
			points[index++] = selectionY2 = bounds.y + bounds.height;
		} else {
			int[] ltt = drawCircle(circX, circY, radius, LEFT_BOTTOM);
			startX = ltt[6];
			System.arraycopy(ltt, 0, points, index, ltt.length);
			index += ltt.length;
