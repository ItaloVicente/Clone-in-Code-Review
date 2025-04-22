
	private static IStatus createMultiStatus(CoreException originalException,
			CoreException e) {
		IStatus childStatus = Activator.createErrorStatus(
				originalException.getMessage(), originalException);
		return new MultiStatus(Activator.getPluginId(), IStatus.ERROR,
				new IStatus[] { childStatus }, e.getMessage(), e);
	}

