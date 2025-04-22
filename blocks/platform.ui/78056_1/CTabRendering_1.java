	private static void mirrorCirclePoints(int[] circle) {
		for (int i = 0; i < circle.length / 2; i += 2) {
			int tmp = circle[i];
			circle[i] = circle[circle.length - i - 2];
			circle[circle.length - i - 2] = tmp;
			tmp = circle[i + 1];
			circle[i + 1] = circle[circle.length - i - 1];
			circle[circle.length - i - 1] = tmp;
		}
	}

	private static int[] drawCircleForTabs(int xC, int yC, int r, CirclePart circlePart) {
		int[] circle = drawCircle(xC, yC, r, circlePart);
		if (!circlePart.isBottom()) {
			mirrorCirclePoints(circle);
		}
		return circle;
	}

	static int[] drawCircle(int xC, int yC, int r, CirclePart circlePart) {
