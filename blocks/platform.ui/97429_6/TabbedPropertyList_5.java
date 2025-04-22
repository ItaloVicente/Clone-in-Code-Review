	public void setListBackgroundColor(Color color) {
		listBackground = color;
	}

	public Color getListBackgroundColor() {
		return listBackground;
	}

	public void setWidgetBackgroundColor(Color color) {
		widgetBackground = color;

		RGB black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK).getRGB();
		RGB white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE).getRGB();

		defaultGradientStart = factory.getColors().createColor("TabbedPropertyList.defaultTabGradientStart", //$NON-NLS-1$
				FormColors.blend(widgetBackground.getRGB(), FormColors.blend(white, widgetNormalShadow.getRGB(), 20),
						60));
		defaultGradientEnd = factory.getColors().createColor("TabbedPropertyList.defaultTabGradientEnd", //$NON-NLS-1$
				FormColors.blend(widgetBackground.getRGB(), widgetNormalShadow.getRGB(), 40));

		bottomNavigationElementShadowStroke1 = factory.getColors().createColor("TabbedPropertyList.tabShadowStroke1", //$NON-NLS-1$
				FormColors.blend(black, widgetBackground.getRGB(), 10));
		bottomNavigationElementShadowStroke2 = factory.getColors().createColor("TabbedPropertyList.tabShadowStroke2", //$NON-NLS-1$
				FormColors.blend(black, widgetBackground.getRGB(), 5));

		hoverGradientStart = factory.getColors().createColor("TabbedPropertyList.hoverBackgroundGradientStart", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 20));
		hoverGradientEnd = factory.getColors().createColor("TabbedPropertyList.hoverBackgroundGradientEnd", //$NON-NLS-1$
				FormColors.blend(widgetNormalShadow.getRGB(), widgetBackground.getRGB(), 10));

		indentedDefaultBackground = factory.getColors().createColor("TabbedPropertyList.indentedDefaultBackground", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 10));
		indentedHoverBackground = factory.getColors().createColor("TabbedPropertyList.indentedHoverBackground", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 75));
	}

	public Color getWidgetBackgroundColor() {
		return widgetBackground;
	}

	public void setWidgetForegroundColor(Color color) {
		widgetForeground = color;
	}

	public Color getWidgetForegroundColor() {
		return widgetForeground;
	}

	public void setWidgetNormalShadowColor(Color color) {
		widgetNormalShadow = color;

		RGB white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE).getRGB();

		navigationElementShadowStroke = factory.getColors().createColor("TabbedPropertyList.shadowStroke", //$NON-NLS-1$
				FormColors.blend(white, widgetNormalShadow.getRGB(), 55));
	}

	public Color getWidgetNormalShadowColor() {
		return widgetNormalShadow;
	}

	public void setWidgetDarkShadowColor(Color color) {
		widgetDarkShadow = color;
	}

	public Color getWidgetDarkShadowColor() {
		return widgetDarkShadow;
	}

