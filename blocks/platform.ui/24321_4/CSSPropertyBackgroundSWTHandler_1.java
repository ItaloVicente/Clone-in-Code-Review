	private Control getParentControl(WidgetElement widgetElement) {
		Widget parentWidget;
		Node parentNode = widgetElement.getParentNode();
		if (!(parentNode instanceof WidgetElement)) {
			return null;
		}

		parentWidget = (Widget) ((WidgetElement) parentNode).getNativeWidget();
		if (!(parentWidget instanceof Control)) {
			return null;
		}

		return (Control) parentWidget;
	}

