		if (!(element instanceof TreeElement)) {
			return false;
		}

		TreeElement treeElement = (TreeElement) element;
		Tree tree = treeElement.getTree();
		if (SWT_TREE_ARROWS_COLOR.equals(property) && value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color newColor = (Color) engine.convert(value, Color.class, tree.getDisplay());
			treeElement.setTreeArrowsForegroundColor(newColor);
		} else if (SWT_TREE_ARROWS_MODE.equals(property)) {
			treeElement.setTreeArrowsMode(value.getCssText());
