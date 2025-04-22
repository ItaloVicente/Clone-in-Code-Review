
	public static void promptToDeleteLock(final LockFailedException exception) {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {

			public void run() {
				boolean confirmed = MessageDialog.openQuestion(display
						.getActiveShell(), UIText.UIUtils_TitleLockFailed,
						MessageFormat.format(UIText.UIUtils_MessageLockFailed,
								exception.getFile().getAbsolutePath()));
				if (confirmed)
					LockFile.unlock(exception.getFile());
			}
		});
	}
