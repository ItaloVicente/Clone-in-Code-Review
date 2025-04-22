	private DisposeListener disposeListener = e -> {
		synchronized (instances) {
			synchronized (NavigatorSaveablesService.this) {
				if (saveablesProviders != null) {
					for (SaveablesProvider saveablesProvider : saveablesProviders) {
						saveablesProvider.dispose();
