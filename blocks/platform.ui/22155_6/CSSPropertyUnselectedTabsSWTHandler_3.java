		CTabFolder folder = ((CTabFolder) control);
		CTabFolderRenderer renderer = folder.getRenderer();
		if (!(renderer instanceof ICTabRendering)) {
			return;
		}

		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color color = (Color) engine.convert(value, Color.class,
					control.getDisplay());
			((ICTabRendering) renderer).setUnselectedTabsColor(color);
			folder.setBackground(color);
			return;
		}
