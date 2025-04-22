
		useTemplate.setPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (FieldEditor.VALUE.equals(event.getProperty())) {
					showComments.setEnabled(
							((Boolean) event.getNewValue()).booleanValue(),
							formattingGroup);
				}
			}
		});
