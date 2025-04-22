		gc.fillRectangle(x + 1, y + 1, uw, h - 2);

		String s = NLS.bind(WorkbenchMessages.HeapStatus_status, convertToMegString(usedMem),
				convertToMegString(totalMem));
		Point p = gc.textExtent(s);
		int sx = (rect.width - 15 - p.x) / 2 + rect.x + 1;
		int sy = (rect.height - 2 - p.y) / 2 + rect.y + 1;
		gc.setForeground(textCol);
		gc.drawString(s, sx, sy, true);

		if (mark != -1) {
			int ssx = (int) (sw * mark / totalMem) + x + 1;
			paintMark(gc, ssx, y, h);
		}
	}

	private void paintCompositeMaxKnown(GC gc) {
		Rectangle rect = getClientArea();
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;
		int bw = imgBounds.width; // button width
		int dx = x + w - bw - 2; // divider x
		int sw = w - bw - 3; // status width
		int uw = (int) (sw * usedMem / maxMem); // used mem width
		int ux = x + 1 + uw; // used mem right edge
		int tw = (int) (sw * totalMem / maxMem); // current total mem width
		int tx = x + 1 + tw; // current total mem right edge
