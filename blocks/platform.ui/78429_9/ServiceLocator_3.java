				SafeRunner.run(new ISafeRunnable() {
					@Override
					public void run() throws Exception {
						iDisposable.dispose();
					}

					@Override
					public void handleException(Throwable ex) {
						WorkbenchPlugin
								.log(StatusUtil.newStatus(IStatus.ERROR,
										"Error while disposing: " + iDisposable.getClass().getName(), ex)); //$NON-NLS-1$
					}
				});
