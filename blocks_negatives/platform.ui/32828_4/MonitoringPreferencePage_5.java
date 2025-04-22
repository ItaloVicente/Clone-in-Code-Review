	/**
	 * Checks that the capture threshold is less than the log threshold.
	 */
	private class LogThresholdFieldEditor extends IntegerFieldEditor {
		IntegerFieldEditor maxEventSampleTime;

		public LogThresholdFieldEditor(String name, String textLabel, Composite parent) {
			super(name, textLabel, parent);
			this.setupField(parent);
			this.fillIntoGrid(parent, 2);
			this.setEnabled(pluginEnabled, parent);
