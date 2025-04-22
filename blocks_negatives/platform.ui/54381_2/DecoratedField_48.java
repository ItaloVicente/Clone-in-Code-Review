			hoverShell.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent pe) {
					pe.gc.drawString(text, hm, hm);
					if (!MAC) {
						pe.gc.drawPolygon(getPolygon(true));
					}
