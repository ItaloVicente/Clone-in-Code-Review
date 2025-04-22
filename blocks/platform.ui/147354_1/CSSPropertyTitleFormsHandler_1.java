		switch (property.toLowerCase()) {
		case BACKGROUND_COLOR_GRADIENT_TITLEBAR_PROPERTY:
			section.setTitleBarGradientBackground(newColor);
			break;
		case BACKGROUND_COLOR_TITLEBAR_PROPERTY:
			section.setTitleBarBackground(newColor);
			break;
		case BORDER_COLOR_TITLEBAR_PROPERTY:
			section.setTitleBarBorderColor(newColor);
			break;
		case CSSPropertyExpandableCompositeHandler.TITLE_BAR_FOREGROUND:
			section.setTitleBarForeground(newColor);
			break;
		case CSSPropertyFormHandler.TB_TOGGLE:
			section.setToggleColor(newColor);
			break;
		case CSSPropertyFormHandler.TB_TOGGLE_HOVER:
			section.setActiveToggleColor(newColor);
			break;
		default:
			break;
