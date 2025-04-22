		Shell parentShell = getParentShell();
		if (parentShell != null) {
			Rectangle bounds = parentShell.getBounds();
			Rectangle trim = parentShell.computeTrim(0, 0, 0, 0);
			return new Rectangle(bounds.x - trim.x, bounds.y - trim.y, bounds.width - trim.width,
					bounds.height - trim.height);
