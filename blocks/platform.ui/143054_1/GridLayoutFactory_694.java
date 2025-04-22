	public static GridLayoutFactory fillDefaults() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		Point defaultSpacing = LayoutConstants.getSpacing();
		layout.horizontalSpacing = defaultSpacing.x;
		layout.verticalSpacing = defaultSpacing.y;
		return new GridLayoutFactory(layout);
	}
