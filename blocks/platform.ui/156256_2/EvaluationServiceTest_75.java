		IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals("foo")) {
					propertyChanged[0] = true;
				}
