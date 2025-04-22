		if (TAB_BACKGROUND_COLOR.equals(property)) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());
				list.setListBackgroundColor(color);
			}
		} else if (TAB_AREA_BACKGROUND_COLOR.equals(property)) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());
				list.setWidgetBackgroundColor(color);
			}
		} else if (COLOR.equals(property)) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());
				list.setWidgetForegroundColor(color);
			}
		} else if (TAB_NORMAL_SHADOW_COLOR.equals(property)) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());
				list.setWidgetNormalShadowColor(color);
			}
		} else if (TAB_DARK_SHADOW_COLOR.equals(property)) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				Color color = CSSSWTColorHelper.getSWTColor(value, control.getDisplay());
				list.setWidgetDarkShadowColor(color);
			}
