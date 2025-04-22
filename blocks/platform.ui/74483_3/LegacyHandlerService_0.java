	private final void clearActivations() {
		deactivateHandlers(handlerActivations);
		for (IHandlerActivation activation : handlerActivations) {
			IHandler handler = activation.getHandler();
			if (handler != null) {
				try {
					handler.dispose();
				} catch (Exception e) {
					WorkbenchPlugin.log("Failed to dispose handler for " //$NON-NLS-1$
							+ activation.getCommandId(), e);
				} catch (LinkageError e) {
					WorkbenchPlugin.log("Failed to dispose handler for " //$NON-NLS-1$
							+ activation.getCommandId(), e);
				}
			}
		}
		handlerActivations.clear();
	}

