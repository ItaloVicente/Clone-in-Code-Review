	@Override
	public long getApproximateObjectCount() {
		long count = 0;
		for (Pack p : getPacks()) {
			try {
				count += p.getIndex().getObjectCount();
			} catch (IOException e) {
				return -1;
			}
		}
		return count;
	}

