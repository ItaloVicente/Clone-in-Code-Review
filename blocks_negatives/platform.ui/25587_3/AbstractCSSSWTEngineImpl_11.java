	@Override
	public Element getElement(Object element) {
		if (element instanceof CSSStylableElement
				&& ((CSSStylableElement) element).getNativeWidget() instanceof Widget) {
			return (CSSStylableElement) element;
		} else if (element instanceof Widget) {
			if (isStylable((Widget) element)) {
				return super.getElement(element);
			}
		} else {
			return super.getElement(element);
		}
		return null;
	}

	/**
	 * Return true if the given widget can be styled
	 *
	 * @param widget
	 *            the widget
	 * @return true if the widget can be styled
	 */
	protected boolean isStylable(Widget widget) {
		return !Boolean.TRUE.equals(widget
	}

