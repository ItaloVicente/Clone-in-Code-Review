		switch (property.toLowerCase()) {
		case SWT_SELECTION_FOREGROUND_COLOR:
			treeElement.setSelectionForegroundColor(newColor);
			break;
		case SWT_SELECTION_BACKGROUND_COLOR:
			treeElement.setSelectionBackgroundColor(newColor);
			break;
		case SWT_SELECTION_BORDER_COLOR:
			treeElement.setSelectionBorderColor(newColor);
			break;
		case SWT_HOT_BACKGROUND_COLOR:
			treeElement.setHotBackgroundColor(newColor);
			break;
		case SWT_HOT_BORDER_COLOR:
			treeElement.setHotBorderColor(newColor);
			break;
