		Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());

		switch (property) {
		case TAB_BACKGROUND_COLOR:
			list.setListBackgroundColor(color);
			break;
		case TAB_AREA_BACKGROUND_COLOR:
			list.setWidgetBackgroundColor(color);
			break;
		case COLOR:
			list.setWidgetForegroundColor(color);
			break;
		case TAB_NORMAL_SHADOW_COLOR:
			list.setWidgetNormalShadowColor(color);
			break;
		case TAB_DARK_SHADOW_COLOR:
			list.setWidgetDarkShadowColor(color);
			break;
