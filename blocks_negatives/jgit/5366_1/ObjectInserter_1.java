	static private final int tempBufSize;
	static {
		String s = System.getProperty("jgit.tempbufmaxsize");
		if (s != null)
			tempBufSize = Integer.parseInt(s);
		else
			tempBufSize = 1000000;
	}

	/**
	 * @param hintSize
	 * @return a temporary byte array for use by the caller
	 */
	protected byte[] buffer(long hintSize) {
		if (hintSize >= tempBufSize)
			tempBuffer = new byte[0];
		else if (tempBuffer == null)
			tempBuffer = new byte[(int) hintSize];
		else if (tempBuffer.length < hintSize)
			tempBuffer = new byte[(int) hintSize];
		return tempBuffer;
	}

