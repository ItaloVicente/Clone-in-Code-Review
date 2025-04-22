	@Override
	public int open() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				doSearch();
			}
		});
		return super.open();
	}

