	private Display getDisplay() {
		return PlatformUI.getWorkbench().getDisplay();
	}

	@Override
	protected void setContentDescription(String description) {
		Runnable uiTask = () -> {
			if (!isDisposed()) {
				super.setContentDescription(description);
			}
		};
		if (Display.getCurrent() == null) {
			getDisplay().asyncExec(uiTask);
		} else {
			uiTask.run();
		}
	}

	private boolean isDisposed() {
		return fFilteredTree == null || fFilteredTree.isDisposed();
	}

