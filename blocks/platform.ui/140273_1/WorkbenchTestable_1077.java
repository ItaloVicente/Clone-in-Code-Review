		Assert.isNotNull(workbench);
		oldAutomatedMode = ErrorDialog.AUTOMATED_MODE;
		ErrorDialog.AUTOMATED_MODE = true;
		oldIgnoreErrors = SafeRunnable.getIgnoreErrors();
		SafeRunnable.setIgnoreErrors(true);
	}
