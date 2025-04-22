	protected void drawDot(final Color outline, final Color fill, final int x,
			final int y, final int w, final int h) {
		int dotX = cellX + x + 2;
		int dotY = cellY + y + 1;
		int dotW = w - 2;
		int dotH = h - 2;
		g.setBackground(fill);
		g.fillOval(dotX, dotY, dotW, dotH);
		g.setForeground(outline);
		g.setLineWidth(2);
		g.drawOval(dotX, dotY, dotW, dotH);
	}

