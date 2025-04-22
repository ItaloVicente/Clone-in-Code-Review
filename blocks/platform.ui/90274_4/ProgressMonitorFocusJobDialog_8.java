		runInWorkspace.addSelectionListener(widgetSelectedAdapter(e -> {
			Rectangle shellPosition = getShell().getBounds();
			job.setProperty(IProgressConstants.PROPERTY_IN_DIALOG, Boolean.FALSE);
			finishedRun();
			ProgressManagerUtil.animateDown(shellPosition);
		}));
