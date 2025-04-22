	private void createIntegerFieldEditor(String name, String labelText, Composite parent) {
		IntegerFieldEditor field = new IntegerFieldEditor(name, labelText, parent);
		field.setValidRange(1, Integer.MAX_VALUE);
		field.setErrorMessage(Messages.MonitoringPreferencePage_invalid_number_error);
		field.fillIntoGrid(parent, 2);
		addField(field, parent);
		field.setEnabled(pluginEnabled, parent);
