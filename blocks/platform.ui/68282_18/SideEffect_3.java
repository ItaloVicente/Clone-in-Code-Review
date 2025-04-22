	@Override
	public void addDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		if (null == this.disposalConsumer) {
			this.disposalConsumer = new ListenerList<>();
		}
		this.disposalConsumer.add(disposalConsumer);
	}

	@Override
	public void removeDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		this.disposalConsumer.remove(disposalConsumer);
	}

