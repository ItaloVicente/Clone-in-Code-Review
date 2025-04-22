	private static final TreeItemPaintListener treeItemArrowsPaintListener = new TreeItemPaintListener() {

		@Override
		protected void draw(TreeItem treeItem, Color foreground, Color background, Event event) {
			GC gc = event.gc;

			int w = 9;
			int h = 9;
			int x = event.x - 16;
			int y = event.y + 4;

			int halfH = h / 2;

			if (!treeItem.getExpanded()) {
				int px0 = x + 1;
				int py0 = y + 1;

				int py1 = y + halfH + 1;
				int px1 = x + (w / 2) + 1;
				int py2 = y + h;

				gc.drawLine(px0, py0, px0, py2);
				gc.drawLine(px0, py0, px1, py1);
				gc.drawLine(px0, py2, px1, py1);
			} else {
				int px0 = x;
				int py0 = y;
				int px1 = x + w - 2;
				int py2 = y + h - 2;

				gc.setBackground(foreground);
				gc.fillPolygon(new int[] { px1, py0, px1, py2, px0, py2, px1, py0 });
				gc.setBackground(background);
			}
			event.detail &= ~SWT.BACKGROUND;
		}
	};

