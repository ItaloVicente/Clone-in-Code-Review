
	@Override
	public void dispose() {
		super.dispose();
		for (Resource disposable : this.disposables) {
			disposable.dispose();
		}
	}
