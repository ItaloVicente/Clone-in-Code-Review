
	public static void setCssStyling(Widget widget, boolean enabled) {
		widget.setData(CSS_DISABLED_KEY, Boolean.valueOf(!enabled));
	}

	public static void setCssClass(Widget widget, String cssClass) {
		widget.setData(CSS_CLASS_KEY, cssClass);
	}
