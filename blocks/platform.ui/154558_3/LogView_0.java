	private Display getDisplay() {
		return PlatformUI.getWorkbench().getDisplay();
	}

	@Override
	protected void setContentDescription(String description) {
		getDisplay().asyncExec(() -> {
			if (fFilteredTree == null || fFilteredTree.isDisposed()) {
				return;
			}
			super.setContentDescription(description);
		});
	}

