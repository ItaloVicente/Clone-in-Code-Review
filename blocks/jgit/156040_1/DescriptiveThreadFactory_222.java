	@Override
	public Thread newThread(final Runnable r) {
		if (r instanceof DescriptiveRunnable) {
			return new Thread(r
		}
		return new Thread(r);
	}
