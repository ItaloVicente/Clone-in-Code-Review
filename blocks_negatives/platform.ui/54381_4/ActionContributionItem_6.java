					resultListener = new IPropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent event) {
							if (event.getProperty().equals(IAction.RESULT)) {
								if (event.getNewValue() instanceof Boolean) {
									result = (Boolean) event.getNewValue();
								}
