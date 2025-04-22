	private void refreshPage() {
		lastObject = null;
		myPage.getSite().getShell().getDisplay()
				.asyncExec(() -> myPage.setPropertySourceProvider(this));
	}

