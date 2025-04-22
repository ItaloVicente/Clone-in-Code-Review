			if (!(control instanceof CTabFolder)) {
				return;
			}
			if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
				Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
				CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
				if (renderer instanceof ICTabRendering) {
					((ICTabRendering) renderer).setUnselectedHotTabsColorBackground(newColor);
				}
