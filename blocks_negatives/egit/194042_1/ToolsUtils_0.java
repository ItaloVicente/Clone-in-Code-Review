	/**
	 * @param textHeader
	 * @param message
	 */
	public static void informUserAboutError(String textHeader, String message) {
		IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				message);
		Runnable runnable = () -> ErrorDialog.openError(null, textHeader,
				null, status);
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
		} else {
			runnable.run();
		}
	}

