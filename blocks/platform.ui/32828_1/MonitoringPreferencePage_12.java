        if (event.getProperty().equals(FieldEditor.VALUE)) {
    		Object source = event.getSource();
    		if (source instanceof FieldEditor) {
    			String preferenceName = ((FieldEditor) source).getPreferenceName();
				if (preferenceName.equals(PreferenceConstants.MONITORING_ENABLED)) {
    				boolean enabled = Boolean.TRUE.equals(event.getNewValue());
	    			for (Map.Entry<FieldEditor, Composite> entry : editors.entrySet()) {
						FieldEditor editor = entry.getKey();
	    				if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
	    					editor.setEnabled(enabled, entry.getValue());
	    				}
	    			}
    			}
    		}
        }
