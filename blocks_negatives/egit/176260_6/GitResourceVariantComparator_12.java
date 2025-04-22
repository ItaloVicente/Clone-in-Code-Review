	private void logException(Exception e) {
		IStatus error = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				e.getMessage(), e);
		Activator.getDefault().getLog().log(error);
	}

