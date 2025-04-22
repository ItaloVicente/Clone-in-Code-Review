		    for (TreeItem item : items) {
			DecorationResult decoration = getDecoration(item.getData());
			item.setBackground(decoration.getBackgroundColor());
			item.setForeground(decoration.getForegroundColor());
			item.setFont(decoration.getFont());
			setColorsAndFonts(item.getItems());
		    }
