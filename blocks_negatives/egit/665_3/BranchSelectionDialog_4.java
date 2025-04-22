		confirmationBtn.setEnabled(branchTree.getSelectionCount() != 0);
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
