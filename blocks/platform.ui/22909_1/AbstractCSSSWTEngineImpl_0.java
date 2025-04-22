	private boolean isApplicableToReset(WidgetElement element) {
		if (element.getNativeWidget() instanceof Widget) {
			return !((Widget) element.getNativeWidget()).isDisposed();
		}
		return false;
	}

