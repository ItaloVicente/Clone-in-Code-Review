	@Override
	public void create() {
		super.create();
		setTaskbarState(getShell(), SWT.PAUSED, 100);
	}

	@Override
	public int open() {
		int result = super.open();
		setTaskbarState(getShell(), SWT.NORMAL, 0);
		return result;
	}

	private void setTaskbarState(Shell shell, int progressState, int progress) {
		if (shell == null) {
			return;
		}
		Display display = shell.getDisplay();
		if (display == null) {
			return;
		}
		TaskBar taskbar = display.getSystemTaskBar();
		if (taskbar == null) {
			return;
		}
		TaskItem taskItem = taskbar.getItem(shell);
		if (taskItem != null) {
			taskItem.setProgressState(progressState);
			taskItem.setProgress(progress);
		}
	}

