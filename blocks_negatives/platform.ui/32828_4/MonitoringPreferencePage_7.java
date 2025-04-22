		Object object = event.getSource();
		if (object instanceof BooleanFieldEditor
				&& ((BooleanFieldEditor) object).getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
			for (Map.Entry<FieldEditor, Composite> editor : editors.entrySet()) {
				editor.getKey().setEnabled(event.getNewValue().equals(true),
						editor.getValue());
			}
		}
