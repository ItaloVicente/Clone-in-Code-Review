			for (int i = 0; i < items.length; i++) {
				PreviewDecoration decoration = getDecoration(items[i].getData());
				items[i].setBackground(decoration.getBackgroundColor());
				items[i].setForeground(decoration.getForegroundColor());
				items[i].setFont(decoration.getFont());
				setColorsAndFonts(items[i].getItems());
			}
