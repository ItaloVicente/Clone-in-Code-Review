    private IPropertyChangeListener listener = new IPropertyChangeListener() {

        @Override
		public void propertyChange(PropertyChangeEvent event) {
            fire(event);
        }
    };
