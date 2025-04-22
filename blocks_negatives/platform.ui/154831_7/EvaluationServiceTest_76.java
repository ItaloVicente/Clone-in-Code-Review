		IPropertyChangeListener propertyShouldChangeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals("foo")) {
					propertyShouldChange[0] = true;
				}

