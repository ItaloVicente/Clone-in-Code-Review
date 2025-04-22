			PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (BrowserViewer.PROPERTY_TITLE.equals(event.getPropertyName())) {
						setPartName((String) event.getNewValue());
					}
