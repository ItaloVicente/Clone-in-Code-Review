		final Display display = getSite().getShell().getDisplay();
		display.asyncExec(() -> {
			setContentDescription(titleSummary);
			fFilteredTree.getViewer().refresh();
			fFilteredTree.setEnabled(true);
		});
