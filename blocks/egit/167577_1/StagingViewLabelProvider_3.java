	private void postLabelEvent() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(() -> {
			fireLabelProviderChanged(new LabelProviderChangedEvent(this));
		});
	}

