				folder.setRedraw(true);
			}
		} else if (widget instanceof Control) {
			Control control = (Control) widget;
			if (control.getFont() != font) {
				CSSSWTFontHelper.storeDefaultFont(control);
				control.setFont(font);
