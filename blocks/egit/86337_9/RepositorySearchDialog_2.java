		long start = System.currentTimeMillis();
		final int[] dirCount = new int[1];
		final SubMonitor m = SubMonitor.convert(monitor);
		try {
			SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
				private long lastMonitorUpdate;
