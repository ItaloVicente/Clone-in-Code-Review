    private IPropertyListener propertyChangeListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            partPropertyChanged(source, propId);
        }
    };
