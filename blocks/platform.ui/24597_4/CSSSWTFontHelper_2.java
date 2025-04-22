			if (defaultFont.isDisposed()) {
				defaultFont = item.getDisplay().getSystemFont();
			}
			if (!equals(defaultFont, item.getFont())) {
				item.setFont(defaultFont);
			}
