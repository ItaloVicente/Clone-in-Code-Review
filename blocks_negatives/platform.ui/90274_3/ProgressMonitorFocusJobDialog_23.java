		runInWorkspace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Rectangle shellPosition = getShell().getBounds();
				job.setProperty(IProgressConstants.PROPERTY_IN_DIALOG,
						Boolean.FALSE);
				finishedRun();
				ProgressManagerUtil.animateDown(shellPosition);
			}
		});
