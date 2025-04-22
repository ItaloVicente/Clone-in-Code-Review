			if (defaultFont.isDisposed()) {
				defaultFont = control.getDisplay().getSystemFont();
			}
			if (!equals(defaultFont, control.getFont())) {
				control.setFont(defaultFont);
			}
