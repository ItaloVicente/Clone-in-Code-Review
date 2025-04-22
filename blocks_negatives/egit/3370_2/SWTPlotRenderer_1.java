		g.setBackground(sys_blue);
		g.fillOval(cellX + x, cellY + y, w, h);
		g.setForeground(sys_darkblue);
		g.setLineWidth(2);
		g.drawOval(cellX + x + 1, cellY + y + 1, w - 2, h - 2);
		g.setForeground(sys_black);
		g.setLineWidth(1);
		g.drawOval(cellX + x, cellY + y, w, h);
