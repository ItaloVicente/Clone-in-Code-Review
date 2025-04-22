			public void doubleClick(final DoubleClickEvent event) {
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						handleDoubleClick(event);
					}
				});
