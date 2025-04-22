	protected Control createCustomArea(Composite parent) {

		if (result.getStatus() != Status.STOPPED) {
			createToggleButton(parent);
			return null;
		}
