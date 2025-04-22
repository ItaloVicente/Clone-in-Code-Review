							SafeRunner.run(new ISafeRunnable() {
								@Override
								public void run() throws Exception {
									(contentChangeListener).contentChanged(element);
								}
								@Override
								public void handleException(Throwable exception) {
								}
							});
