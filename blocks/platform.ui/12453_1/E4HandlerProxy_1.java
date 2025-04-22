
	public void addHandlerListener(IHandlerListener handlerListener) {
		handler.addHandlerListener(handlerListener);
	}

	public void dispose() {
		handler.dispose();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (logExecute) {
			logExecute = false;
			Status status = new Status(IStatus.WARNING, "org.eclipse.ui", //$NON-NLS-1$
					"Called handled proxy execute(*) directly" + command, new Exception()); //$NON-NLS-1$
			WorkbenchPlugin.log(status);
		}
		return null;
	}

	public boolean isEnabled() {
		return handler.isEnabled();
	}

	public boolean isHandled() {
		return handler.isHandled();
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		handler.removeHandlerListener(handlerListener);
	}

	public void setEnabled(Object evaluationContext) {
		if (logSetEnabled) {
			logSetEnabled = false;
			Status status = new Status(IStatus.WARNING, "org.eclipse.ui", //$NON-NLS-1$
					"Called handled proxy setEnabled(*) directly" + command, new Exception()); //$NON-NLS-1$
			WorkbenchPlugin.log(status);
		}
	}
