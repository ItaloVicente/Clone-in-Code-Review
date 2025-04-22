		g.setLineWidth(2);

		g.setAlpha(128);
		g.setForeground(sys_gray);
		g.drawRoundRectangle(cellX + x, cellY + texty -2, textsz.x + 5, textsz.y + 3, arc, arc);
		g.setLineWidth(2);
		g.setForeground(sys_black);
		g.drawRoundRectangle(cellX + x + 1, cellY + texty -1, textsz.x + 3, textsz.y + 1, arc, arc);
		g.setAlpha(255);
