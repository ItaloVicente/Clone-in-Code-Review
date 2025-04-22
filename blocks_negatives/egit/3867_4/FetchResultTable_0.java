		final IStyledLabelProvider styleProvider = new IStyledLabelProvider() {

			private final WorkbenchLabelProvider wrapped = new WorkbenchLabelProvider();

			public void removeListener(ILabelProviderListener listener) {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void dispose() {
				wrapped.dispose();
			}

			public void addListener(ILabelProviderListener listener) {
			}
