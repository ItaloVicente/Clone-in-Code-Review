			if (SWT_TREE_ARROWS_FG_COLOR.equals(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, tree.getDisplay());
					treeElement.setTreeArrowsForegroundColor(newColor);
				}
			} else if (SWT_TREE_ARROWS_MODE.equals(property)) {
