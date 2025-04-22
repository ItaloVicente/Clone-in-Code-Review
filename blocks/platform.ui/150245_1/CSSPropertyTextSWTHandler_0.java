		if (newColor != null && newColor.isDisposed() || value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return;
		}

		if (widget instanceof CTabItem) {
			CTabFolder folder = ((CTabItem) widget).getParent();
			if ("selected".equals(pseudo)) {
				CSSSWTColorHelper.setSelectionForeground(folder, newColor);
			} else {
				CSSSWTColorHelper.setForeground(folder, newColor);
