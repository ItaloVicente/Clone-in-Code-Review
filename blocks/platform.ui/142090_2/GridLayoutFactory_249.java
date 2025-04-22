	public GridLayoutFactory extendedMargins(Rectangle differenceRect) {
		l.marginLeft = -differenceRect.x;
		l.marginTop = -differenceRect.y;
		l.marginBottom = differenceRect.y + differenceRect.height;
		l.marginRight = differenceRect.x + differenceRect.width;
		return this;
	}
