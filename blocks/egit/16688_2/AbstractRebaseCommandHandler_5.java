	private static IStatus createMultiStatus(Throwable originalException,
			Throwable e) {
		IStatus childStatus = Activator.error(originalException.getMessage(),
				originalException);
		return new MultiStatus(Activator.getPluginId(), IStatus.ERROR,
				new IStatus[] { childStatus }, e.getMessage(), e);
	}

