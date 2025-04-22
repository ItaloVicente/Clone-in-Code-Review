		int minWidth;
		GC gc = new GC(planTreeViewer.getControl().getDisplay());
		try {
			gc.setFont(planTreeViewer.getControl().getFont());
			minWidth = Math.max(gc.stringExtent("0000000").x, //$NON-NLS-1$
					gc.stringExtent(headings[3]).x) + 10;
		} finally {
			gc.dispose();
		}
