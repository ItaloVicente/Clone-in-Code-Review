    private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            RetargetAction.this.propagateChange(event);
        }
    };
