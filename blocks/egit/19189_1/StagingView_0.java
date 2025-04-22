		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				refreshViewersInternal();
			}
		});
	}

	public void refreshViewersPreservingExpandedElements() {
