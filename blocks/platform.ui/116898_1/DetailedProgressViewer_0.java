	private int getRecommendedProgressItemSize() {
		long maxMemory = Runtime.getRuntime().maxMemory();
		maxMemory = maxMemory / 1024;
		maxMemory = maxMemory / 1024;
		return (int) (maxMemory / 100);
	}

