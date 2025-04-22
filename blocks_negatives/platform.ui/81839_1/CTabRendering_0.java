	private static int[] drawCircleForTabs(int xC, int yC, int r, CirclePart circlePart) {
		int[] circle = drawCircle(xC, yC, r, circlePart);
		if (!circlePart.isBottom()) {
			mirrorCirclePoints(circle);
		}
		return circle;
	}

