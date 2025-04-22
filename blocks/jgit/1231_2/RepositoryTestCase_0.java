
	public static long fsTick(File lastFile) throws InterruptedException
			IOException {
		long sleepTime = 1;
		File tmp = File.createTempFile("FileTreeIteratorWithTimeControl"
		try {
			long startTime = (lastFile == null) ? tmp.lastModified() : lastFile
					.lastModified();
			long actTime = tmp.lastModified();
			while (actTime <= startTime) {
				Thread.sleep(sleepTime);
				sleepTime *= 5;
				tmp.setLastModified(System.currentTimeMillis());
				actTime = tmp.lastModified();
			}
			return actTime;
		} finally {
			tmp.delete();
		}
	}
