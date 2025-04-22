
	public void updateElement(UIElement element, Map parameters) {
		if (handler instanceof IElementUpdater) {
			((IElementUpdater) handler).updateElement(element, parameters);
		}
	}
