				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					if (SWT_SELECTION_FOREGROUND_COLOR.equals(property)) {
						Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
						treeElement.setSelectionForegroundColor(newColor);
					} else if (SWT_SELECTION_BACKGROUND_COLOR.equals(property)) {
						Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
						treeElement.setSelectionBackgroundColor(newColor);
					} else if (SWT_SELECTION_BORDER_COLOR.equals(property)) {
						Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
						treeElement.setSelectionBorderColor(newColor);
					} else if (SWT_HOT_BACKGROUND_COLOR.equals(property)) {
						Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
						treeElement.setHotBackgroundColor(newColor);
					} else if (SWT_HOT_BORDER_COLOR.equals(property)) {
						Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
						treeElement.setHotBorderColor(newColor);
					}
				}
			}
