		GC textGC = new GC(textBuffer, gc.getStyle());
		textGC.setForeground(fg);
		textGC.setBackground(bg);
		textGC.setFont(getFont());
		textGC.fillRectangle(0, 0, width, height);
		Rectangle repaintRegion = new Rectangle(x, y, width, height);
