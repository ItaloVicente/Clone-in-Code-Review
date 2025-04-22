
	@Override
	public void updateElement(UIElement element, Map parameters) {
		RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemoteCached(getRepository());
		if (config != null) {
			element.setText(SimpleConfigurePushDialog
					.getSimplePushCommandLabel(config));
		}
	}
