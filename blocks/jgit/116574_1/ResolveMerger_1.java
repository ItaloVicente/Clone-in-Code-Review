	private long getEntryContentLength(File file
			throws IOException {
		if (streamType == EolStreamType.DIRECT) {
			return file.length();
		}
		long length = 0;
		try (InputStream is = EolStreamTypeUtil.wrapInputStream(
				new BufferedInputStream(new FileInputStream(file))
				streamType)) {
			for (;;) {
				long n = is.skip(1 << 20);
				if (n <= 0) {
					break;
				}
				length += n;
			}
			return length;
		}
	}

