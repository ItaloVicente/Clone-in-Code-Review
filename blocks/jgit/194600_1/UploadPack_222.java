	@Override
	public void close() {
		if (timer != null) {
			try {
				timer.terminate();
			} finally {
				timer = null;
			}
		}
	}

