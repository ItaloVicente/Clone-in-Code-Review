	@Override
	public void addDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		checkRealm();
		if (isDisposed()) {
			return;
		}
		if (this.disposeListeners == null) {
			this.disposeListeners = new ArrayList<>();
		}
		this.disposeListeners.add(disposalConsumer);
	}

	@Override
	public void removeDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		checkRealm();
		if (this.disposeListeners == null) {
			return;
		}
		this.disposeListeners.remove(disposalConsumer);
	}

