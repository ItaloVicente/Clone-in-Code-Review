		if (entry instanceof IPropertySheetEntry2) {
			IPropertySheetEntry2 entry2 = (IPropertySheetEntry2) entry;

			Color color = entry2.getForeground();
			if (item.getForeground() != color) {
				item.setForeground(color);
			}

			color = entry2.getBackground();
			if (item.getBackground()!= color) {
				item.setBackground(color);
			}

			Font font = entry2.getFont();
			if (item.getFont() != font) {
				item.setFont(font);
			}
		}

