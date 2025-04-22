			enablementListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty() == PROP_ENABLED) {
						if (event.getNewValue() instanceof Boolean) {
							setEnabled(((Boolean) event.getNewValue())
									.booleanValue());
						} else {
							setEnabled(false);
						}
