	private Color getBackground() {
		Color themeBg = display.getActiveShell().getBackground();
		Color impliedBg = getImpliedBackground();
		if (themeBg == null) {
			return impliedBg;
		}
		if (absoluteDifference(themeBg.getRGB().red, impliedBg.getRGB().red) < 40
				&& absoluteDifference(themeBg.getRGB().blue, impliedBg.getRGB().blue) < 40
				&& absoluteDifference(themeBg.getRGB().green, impliedBg.getRGB().green) < 40) {
			return impliedBg;
		}
		return themeBg;
	}

	private Color getImpliedBackground() {
		return this.display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	private int absoluteDifference(int a, int b) {
		return Math.abs(a - b);
	}

	private void addRegion(Shell shell) {
		Region region = new Region();
		Point s = shell.getSize();

		/* Add entire Shell */
		region.add(0, 0, s.x, s.y);

		/* Subtract Top-Left Corner */
		region.subtract(0, 0, 5, 1);
		region.subtract(0, 1, 3, 1);
		region.subtract(0, 2, 2, 1);
		region.subtract(0, 3, 1, 1);
		region.subtract(0, 4, 1, 1);

		/* Subtract Top-Right Corner */
		region.subtract(s.x - 5, 0, 5, 1);
		region.subtract(s.x - 3, 1, 3, 1);
		region.subtract(s.x - 2, 2, 2, 1);
		region.subtract(s.x - 1, 3, 1, 1);
		region.subtract(s.x - 1, 4, 1, 1);

		/* Subtract Bottom-Left Corner */
		region.subtract(0, s.y, 5, 1);
		region.subtract(0, s.y - 1, 3, 1);
		region.subtract(0, s.y - 2, 2, 1);
		region.subtract(0, s.y - 3, 1, 1);
		region.subtract(0, s.y - 4, 1, 1);

		/* Subtract Bottom-Right Corner */
		region.subtract(s.x - 5, s.y - 0, 5, 1);
		region.subtract(s.x - 3, s.y - 1, 3, 1);
		region.subtract(s.x - 2, s.y - 2, 2, 1);
		region.subtract(s.x - 1, s.y - 3, 1, 1);
		region.subtract(s.x - 1, s.y - 4, 1, 1);

		/* Dispose old first */
		if (shell.getRegion() != null) {
			shell.getRegion().dispose();
		}

		/* Apply Region */
		shell.setRegion(region);

		/* Remember to dispose later */
		this.lastUsedRegion = region;
	}

