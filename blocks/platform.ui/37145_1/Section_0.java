	private void updateHeaderImage(Color bg, Rectangle bounds, int theight, int realtheight) {
		Image image = null;
		if (getTitleBarGradientBackground() != null) {
			image = FormImages.getInstance().getSectionGradientImage(getBackground(), getTitleBarGradientBackground(),
					bg, realtheight,
					theight, marginHeight,
					getDisplay());
		} else {
			image = FormImages.getInstance().getImage(getBackground(), bg, realtheight, theight,
					marginHeight,
					getDisplay());
		}
