	private void clearActivations() {
		deactivateHandlers(handlerActivations);
		for (IHandlerActivation activation : handlerActivations) {
			final IHandler handler = activation.getHandler();
			if (handler != null) {
				SafeRunner.run(new ISafeRunnable() {

					@Override
					public void run() throws Exception {
						handler.dispose();
					}

					@Override
					public void handleException(Throwable exception) {
						WorkbenchPlugin.log("Failed to dispose handler for " //$NON-NLS-1$
								+ activation.getCommandId(), exception);
					}

				});
			}
		}
		handlerActivations.clear();
	}

