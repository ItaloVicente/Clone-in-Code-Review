
	public static boolean handleLockDelete(final LockFailedException exception) {
		if (!promptToDeleteLock(exception))
			return false;
		return LockFile.unlock(exception.getFile());
	}

	public static boolean promptToDeleteLock(final LockFailedException exception) {
		final AtomicBoolean confirmed = new AtomicBoolean();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(new Runnable() {

			public void run() {
				confirmed.set(MessageDialog.openQuestion(display
						.getActiveShell(), UIText.UIUtils_TitleLockFailed,
						MessageFormat.format(UIText.UIUtils_MessageLockFailed,
								exception.getFile().getAbsolutePath())));
			}
		});
		return confirmed.get();
	}
