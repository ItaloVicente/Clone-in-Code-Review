    private IPropertyChangeListener fontAndColorListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            if (!disposed) {
                setColorsAndFonts();
                viewForm.layout(true);
            }
        }
    };
