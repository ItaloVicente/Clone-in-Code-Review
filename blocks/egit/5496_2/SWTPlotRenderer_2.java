		g.setLineWidth(1);
		g.setBackground(sys_white);
		g.fillRoundRectangle(cellX + x, cellY + texty - 2, textsz.x + 7,
				textsz.y + 3, arc, arc);
		g.setBackground(resources.createColor(labelInner));
		g.fillRoundRectangle(cellX + x + 2, cellY + texty, textsz.x + 4,
				textsz.y, arc - 2, arc - 2);
		g.setForeground(resources.createColor(labelOuter));
		g.drawRoundRectangle(cellX + x, cellY + texty - 2, textsz.x + 7,
				textsz.y + 3, arc, arc);

