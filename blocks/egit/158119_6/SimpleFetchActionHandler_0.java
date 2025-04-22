
	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setText(SimpleConfigureFetchDialog
				.getSimpleFetchCommandLabel(SimpleConfigureFetchDialog
						.getConfiguredRemoteCached(getRepository())));
	}
