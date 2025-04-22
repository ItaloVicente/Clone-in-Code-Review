			@Override
			public void propertyChange(PropertyChangeEvent event) {
				throw new RuntimeException();

			}
		};
		IPropertyChangeListener goodListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				result[0] = true;

			}
