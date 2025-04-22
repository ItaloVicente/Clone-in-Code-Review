			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					((INestable) service).deactivate();
				}

				@Override
				public void handleException(Throwable ex) {
					WorkbenchPlugin
							.log(StatusUtil.newStatus(IStatus.ERROR, "Error while deactivating: " + service, ex)); //$NON-NLS-1$
				}
			});
