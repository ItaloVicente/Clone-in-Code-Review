			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					((INestable) service).activate();
				}

				@Override
				public void handleException(Throwable ex) {
					WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.ERROR, "Error while activating: " + service, ex)); //$NON-NLS-1$
				}
			});
