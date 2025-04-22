				folder.setRedraw(true);
			}
		} else if (widget instanceof Control) {
			Control control = (Control) widget;
			if (control.getFont() != font) {
				control.setFont(font);
