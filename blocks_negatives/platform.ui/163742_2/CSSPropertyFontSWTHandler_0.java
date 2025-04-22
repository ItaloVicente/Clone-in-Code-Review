			Control control = (Control)widget;
			final boolean isLayoutDeferred = (control instanceof Composite) && ((Composite)control).isLayoutDeferred();
			if (isLayoutDeferred) {
				control.setRedraw(false);
			}
			try {
				CSSSWTFontHelper.setFont(control, font);
			} finally {
				if (isLayoutDeferred) {
					control.setRedraw(true);
				}
			}
