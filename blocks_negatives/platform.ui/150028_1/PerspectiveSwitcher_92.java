	void paint(PaintEvent e) {
		GC gc = e.gc;
		Point size = comp.getSize();
		if (curveColor == null || curveColor.isDisposed()) {
			curveColor = e.display.getSystemColor(SWT.COLOR_GRAY);
		}
		int h = size.y;
		int[] simpleCurve = new int[] { 0, h - 1, 1, h - 1, 2, h - 2, 2, 1, 3, 0 };
		gc.setForeground(curveColor);
		gc.setAdvanced(true);
		if (gc.getAdvanced()) {
			gc.setAntialias(SWT.ON);
		}
		gc.drawPolyline(simpleCurve);

		Rectangle bounds = ((Control) e.widget).getBounds();
		bounds.x = bounds.y = 0;
		Region r = new Region();
		r.add(bounds);
		int[] simpleCurveClose = new int[simpleCurve.length + 4];
		System.arraycopy(simpleCurve, 0, simpleCurveClose, 0, simpleCurve.length);
		int index = simpleCurve.length;
		simpleCurveClose[index++] = bounds.width;
		simpleCurveClose[index++] = 0;
		simpleCurveClose[index++] = bounds.width;
		simpleCurveClose[index++] = bounds.height;
		r.subtract(simpleCurveClose);
		Region clipping = new Region();
		gc.getClipping(clipping);
		r.intersect(clipping);
		gc.setClipping(r);
		Image b = toolParent.getBackgroundImage();
		if (b != null && !b.isDisposed())
			gc.drawImage(b, 0, 0);

		r.dispose();
		clipping.dispose();
	}

