		return RegistryReader.getDescription(definingElement);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean newState) {

		if (this.enabled != newState) {
			this.enabled = newState;
			try {
				refreshDecorator();
			} catch (CoreException exception) {
				handleCoreException(exception);
			}

		}
	}

	protected abstract void refreshDecorator() throws CoreException;

	protected void disposeCachedDecorator(IBaseLabelProvider disposedDecorator) {
		disposedDecorator.removeListener(WorkbenchPlugin.getDefault().getDecoratorManager());
		disposedDecorator.dispose();

	}

	public boolean isAdaptable() {
