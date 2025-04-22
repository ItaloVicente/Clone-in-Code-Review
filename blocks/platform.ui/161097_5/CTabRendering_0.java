	private int[] computeSquareTabOutline(int itemIndex, boolean onBottom, int startX, int endX, int bottomY,
			Rectangle bounds, Point parentSize) {
		int index = 0;
		int outlineY = onBottom ? bottomY + bounds.height : bottomY - bounds.height - 1;
		int[] points = new int[12];

		if (itemIndex == 0 && bounds.x == -computeTrim(CTabFolderRenderer.PART_HEADER, SWT.NONE, 0, 0, 0, 0).x) {
			points[index++] = startX;
			points[index++] = bottomY;
		} else {
			if (active) {
				points[index++] = shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE;
				points[index++] = bottomY;
			}
			points[index++] = startX;
			points[index++] = bottomY;
		}

		points[index++] = startX;
		points[index++] = outlineY;

		points[index++] = endX;
		points[index++] = outlineY;

		points[index++] = endX;
		points[index++] = bottomY;

		if (active) {
			points[index++] = parentSize.x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);
			points[index++] = bottomY;
		}

		int[] tmpPoints = new int[index];
		System.arraycopy(points, 0, tmpPoints, 0, index);

		return tmpPoints;
	}

	private int[] computeRoundTabOutline(int itemIndex, boolean onBottom, int bottomY, Rectangle bounds,
			Point parentSize) {
		int header = shadowEnabled ? 2 : 0;
		int width = bounds.width;
		int[] points = new int[1024];
		int index = 0;
		int radius = cornerSize / 2;
		int circX = bounds.x + radius;
		int circY = onBottom ? bounds.y + bounds.height + 1 - header - radius : bounds.y - 1 + radius;
		if (itemIndex == 0 && bounds.x == -computeTrim(CTabFolderRenderer.PART_HEADER, SWT.NONE, 0, 0, 0, 0).x) {
			circX -= 1;
			points[index++] = circX - radius;
			points[index++] = bottomY;

			points[index++] = circX - radius;
			points[index++] = bottomY;
		} else {
			if (active) {
				points[index++] = shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE;
				points[index++] = bottomY;
			}
			points[index++] = bounds.x;
			points[index++] = bottomY;
		}

		int[] ltt = drawCircle(circX, circY, radius, CirclePart.left(onBottom));
		if (!onBottom) {
			mirrorCirclePoints(ltt);
		}
		System.arraycopy(ltt, 0, points, index, ltt.length);
		index += ltt.length;
		int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, CirclePart.right(onBottom));
		if (!onBottom) {
			mirrorCirclePoints(rt);
		}
		System.arraycopy(rt, 0, points, index, rt.length);
		index += rt.length;

		points[index++] = bounds.width + circX - radius;
		points[index++] = bottomY;

		if (active) {
			points[index++] = parentSize.x
					- (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);
			points[index++] = bottomY;
		}

		int[] tmpPoints = new int[index];
		System.arraycopy(points, 0, tmpPoints, 0, index);
		return tmpPoints;
	}

