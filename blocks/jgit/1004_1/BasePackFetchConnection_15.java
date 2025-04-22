	@Override
	public void close() {
		walk.release();
		super.close();
	}

