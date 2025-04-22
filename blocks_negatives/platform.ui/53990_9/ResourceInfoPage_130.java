			encodingEditor.setPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty().equals(FieldEditor.IS_VALID)) {
						setValid(encodingEditor.isValid());
					}
