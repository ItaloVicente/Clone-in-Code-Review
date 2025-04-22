	private static int drawCirclePoint(int loop, int xC, int yC, int[] points, int x, int y, CirclePart circlePart) {
		switch (circlePart) {
		case RIGHT_BOTTOM:
			points[loop++] = xC + x;
			points[loop++] = yC + y;
			break;
		case RIGHT_TOP:
			points[loop++] = xC + y;
			points[loop++] = yC - x;
			break;
		case LEFT_TOP:
			points[loop++] = xC - x;
			points[loop++] = yC - y;
			break;
		case LEFT_BOTTOM:
			points[loop++] = xC - y;
			points[loop++] = yC + x;
			break;
		}
		return loop;
	}

