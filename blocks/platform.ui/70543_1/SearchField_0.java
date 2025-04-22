		if (Util.isWin32() || Util.isGtk()) {
			FontMetrics fm = gc.getFontMetrics();
			int wHint = QuickAccessMessages.QuickAccess_EnterSearch.length() * fm.getAverageCharWidth();
			int hHint = fm.getHeight();
			gc.dispose();
			text.setSize(text.computeSize(wHint, hHint));
		} else {
			Point p = gc.textExtent(QuickAccessMessages.QuickAccess_EnterSearch);
			Rectangle r = text.computeTrim(0, 0, p.x, p.y);
			gc.dispose();

			GridDataFactory.fillDefaults().hint(r.width - r.x, SWT.DEFAULT).applyTo(text);
		}

