		g.setLineWidth(1);

		g.setBackground(sys_white);
		g.fillRoundRectangle(cellX + x + 1, cellY + texty, textsz.x + 6,
				textsz.y + 1, arc, arc);

		g.setBackground(resources.createColor(labelInner));
		g.fillRoundRectangle(cellX + x + 2, cellY + texty + 1, textsz.x + 4,
				textsz.y - 2, arc - 1, arc - 1);

		g.setForeground(resources.createColor(labelOuter));
		g.drawRoundRectangle(cellX + x, cellY + texty - 1, textsz.x + 7,
				textsz.y + 1, arc, arc);

