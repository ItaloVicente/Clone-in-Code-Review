
	@Override
	public void updateElement(UIElement element, Map parameters) {
		RemoteConfig config = SimpleConfigureFetchDialog
				.getConfiguredRemoteCached(getRepository());
		if (config != null) {
			element.setText(SimpleConfigureFetchDialog
					.getSimpleFetchCommandLabel(config));
		}
	}
