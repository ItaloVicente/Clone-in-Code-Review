	public static void informUser(String textHeader, String message) {
		Runnable runnable = () -> MessageDialog.openInformation(null,
				textHeader, message);
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
		} else {
			runnable.run();
		}
