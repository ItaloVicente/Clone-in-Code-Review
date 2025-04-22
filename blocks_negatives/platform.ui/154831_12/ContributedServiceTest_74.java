		locator = lc.createServiceLocator(parent, new TempLevelFactory(8),
				new IDisposable() {
					@Override
					public void dispose() {
					}
				});
