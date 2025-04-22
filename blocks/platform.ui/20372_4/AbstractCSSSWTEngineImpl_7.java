
	@Override
	public void reset() {
		for (CSSElementContext elementContext : getElementsContext().values()) {
			Element element = elementContext.getElement();
			if (element instanceof WidgetElement) {
				((WidgetElement) element).reset();
			}
		}

		getResourcesRegistry().dispose();
		super.reset();
	}

