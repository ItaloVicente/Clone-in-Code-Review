    private IPropertyChangeListener fontAndColorListener = event -> {
	    if (!disposed) {
	        setColorsAndFonts();
	        viewForm.layout(true);
	    }
	};
