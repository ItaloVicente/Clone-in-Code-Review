		Widget widget = (Widget) ((WidgetElement) element).getNativeWidget();
		Image image = (Image) engine.convert(value, Image.class,
				widget.getDisplay());
		if (widget instanceof CTabFolder && "selected".equals(pseudo)) {
			((CTabFolder) widget).setSelectionBackground(image);
		} else if (widget instanceof Button) {
			Button button = ((Button) widget);
