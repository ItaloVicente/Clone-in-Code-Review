		Widget control = (Widget) ((WidgetElement) element).getNativeWidget();
		Image image = (Image) engine.convert(value, Image.class, control
				.getDisplay());
		if (control instanceof CTabFolder && "selected".equals(pseudo)) {
			((CTabFolder) control).setSelectionBackground(image);
		} else if (control instanceof Button) {
			Button button = ((Button) control);
