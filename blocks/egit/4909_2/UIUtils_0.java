
	public static boolean handleLockDelete(final LockFailedException exception) {
		if (!promptToDeleteLock(exception))
			return false;
		File lock = LockFile.getLockFile(exception.getFile());
		try {
			FileUtils.delete(lock, FileUtils.RETRY | FileUtils.SKIP_MISSING);
			return true;
		} catch (IOException e) {
			return false;
		}
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
