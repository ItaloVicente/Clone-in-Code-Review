		ToolItem restoreBtn = new ToolItem(trimStackTB, SWT.PUSH);
		restoreBtn.setToolTipText(Messages.TrimStack_RestoreText);
		restoreBtn.setImage(getRestoreImage());
		restoreBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED);
			}
		});
