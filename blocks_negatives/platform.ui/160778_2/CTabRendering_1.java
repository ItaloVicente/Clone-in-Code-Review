		int[] ltt = drawCircle(circX + 1, circY + 1, radius, CirclePart.LEFT_TOP);
		System.arraycopy(ltt, 0, points, index, ltt.length);
		index += ltt.length;

		int[] lbb = drawCircle(circX + 1, circY + height - (radius * 2) - 2, radius, CirclePart.LEFT_BOTTOM);
		System.arraycopy(lbb, 0, points, index, lbb.length);
		index += lbb.length;

		int[] rb = drawCircle(circX + width - (radius * 2) - 2, circY + height - (radius * 2) - 2, radius,
				CirclePart.RIGHT_BOTTOM);
		System.arraycopy(rb, 0, points, index, rb.length);
		index += rb.length;

		int[] rt = drawCircle(circX + width - (radius * 2) - 2, circY + 1, radius, CirclePart.RIGHT_TOP);
		System.arraycopy(rt, 0, points, index, rt.length);
		index += rt.length;
		points[index++] = points[0];
		points[index++] = points[1];

		int[] tempPoints = new int[index];
		System.arraycopy(points, 0, tempPoints, 0, index);

