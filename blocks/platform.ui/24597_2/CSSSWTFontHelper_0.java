			if (defaultFont.isDisposed()) {
				defaultFont = control.getDisplay().getSystemFont();
			}
			if (defaultFont != control.getFont()) {
				control.setFont(defaultFont);
			}
