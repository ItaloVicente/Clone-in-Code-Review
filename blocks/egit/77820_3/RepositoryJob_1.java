	@NonNull
	protected IStatus getDeferredStatus() {
		return new Status(IStatus.OK, Activator.getPluginId(), IStatus.OK, "", //$NON-NLS-1$
				null);
	}

