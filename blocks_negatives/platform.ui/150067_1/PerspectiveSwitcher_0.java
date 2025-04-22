	void resize() {
		Point size = comp.getSize();
		Image oldBackgroundImage = backgroundImage;
		backgroundImage = new Image(comp.getDisplay(), size.x, size.y);
		GC gc = new GC(backgroundImage);
		comp.getParent().drawBackground(gc, 0, 0, size.x, size.y, 0, 0);
		Color background = comp.getBackground();
		Color border = comp.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
		RGB backgroundRGB = background.getRGB();
		Color gradientTop = new Color(comp.getDisplay(), backgroundRGB.red + 12, backgroundRGB.green + 10,
				backgroundRGB.blue + 10);
		int h = size.y;
		int curveStart = 0;
		int curve_width = 5;

		int[] curve = new int[] { 0, h, 1, h, 2, h - 1, 3, h - 2, 3, 2, 4, 1, 5, 0, };
		int[] line1 = new int[curve.length + 4];
		int index = 0;
		int x = curveStart;
		line1[index++] = x + 1;
		line1[index++] = h;
		for (int i = 0; i < curve.length / 2; i++) {
			line1[index++] = x + curve[2 * i];
			line1[index++] = curve[2 * i + 1];
		}
		line1[index++] = x + curve_width;
		line1[index++] = 0;

		int[] line2 = new int[line1.length];
		index = 0;
		for (int i = 0; i < line1.length / 2; i++) {
			line2[index] = line1[index++] - 1;
			line2[index] = line1[index++];
		}

		gc.setForeground(gradientTop);
		gc.setBackground(background);
		gc.drawLine(4, 0, size.x, 0);
		gc.drawLine(3, 1, size.x, 1);
		gc.fillGradientRectangle(2, 2, size.x - 2, size.y - 3, true);
		gc.setForeground(background);
		gc.drawLine(2, size.y - 1, size.x, size.y - 1);
		gradientTop.dispose();

		gc.setForeground(border);
		gc.drawPolyline(line2);
		gc.dispose();
		comp.setBackgroundImage(backgroundImage);
		if (oldBackgroundImage != null)
			oldBackgroundImage.dispose();

	}
