
	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setText(SimpleConfigurePushDialog
				.getSimplePushCommandLabel(SimpleConfigurePushDialog
						.getConfiguredRemoteCached(getRepository())));
	}
