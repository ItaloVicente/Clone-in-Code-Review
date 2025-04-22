    private Preferences.IPropertyChangeListener listener = new Preferences.IPropertyChangeListener() {
        @Override
		public void propertyChange(Preferences.PropertyChangeEvent event) {
            firePropertyChange(event.getProperty());
        }
    };
