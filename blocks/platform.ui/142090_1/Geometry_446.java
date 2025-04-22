	}

	public static boolean isHorizontal(int swtSideConstant) {
		return !(swtSideConstant == SWT.LEFT || swtSideConstant == SWT.RIGHT);
	}

	public static void moveRectangle(Rectangle rect, Point delta) {
		rect.x += delta.x;
		rect.y += delta.y;
	}

	public static void expand(Rectangle rect, Rectangle differenceRect) {
		rect.x += differenceRect.x;
		rect.y += differenceRect.y;
		rect.height = Math.max(0, rect.height + differenceRect.height);
		rect.width = Math.max(0, rect.width + differenceRect.width);
	}

