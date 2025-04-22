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

	protected boolean isStylable(Widget widget) {
		return !Boolean.TRUE.equals(widget
				.getData("org.eclipse.e4.ui.css.disabled")); //$NON-NLS-1$
	}

