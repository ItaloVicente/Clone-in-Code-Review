		super.getAddButton().setText(Messages.ListFieldEditor_add_filter_button_label);
		super.getUpButton().setVisible(false);
		super.getDownButton().setVisible(false);
		dialog = new FilterInputDialog(super.getShell());
		this.setEnabled(MonitoringPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.MONITORING_ENABLED), parent);
