	private DisposeListener disposeListener = new DisposeListener() {

		@Override
		public void widgetDisposed(DisposeEvent e) {
			synchronized (instances) {
				synchronized (NavigatorSaveablesService.this) {
					if (saveablesProviders != null) {
						for (SaveablesProvider saveablesProvider : saveablesProviders) {
							saveablesProvider.dispose();
						}
