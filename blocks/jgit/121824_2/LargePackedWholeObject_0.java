
		int bufsz = 8192;
		InputStream in = new BufferedInputStream(
				new InflaterInputStream(packIn
				bufsz);
		return new ObjectStream.Filter(type
