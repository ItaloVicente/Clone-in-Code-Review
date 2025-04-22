
		int shift = Util.isMac() ? -25 : -10;

		Color lightColor = PlatformUI.getWorkbench().getDisplay()
				.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

		RGB darkRGB = new RGB(Math.max(0, lightColor.getRed() + shift), Math
				.max(0, lightColor.getGreen() + shift), Math.max(0, lightColor
				.getBlue()
				+ shift));
		JFaceResources.getColorRegistry().put(DARK_COLOR_KEY, darkRGB);
