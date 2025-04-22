    private IPropertyListener compatibilityTitleListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            if (propId == IWorkbenchPartConstants.PROP_TITLE) {
                setDefaultContentDescription();
            }
        }
    };
