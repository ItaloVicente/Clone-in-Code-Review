			if (defaultFont.isDisposed()) {
				defaultFont = item.getDisplay().getSystemFont();
			}
			if (defaultFont != item.getFont()) {
				item.setFont(defaultFont);
			}
