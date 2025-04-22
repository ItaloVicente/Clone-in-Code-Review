		PackIndex index = readImpl(fd);
		IO.readFully(fd
		return index;
	}

	private static PackIndex readImpl(InputStream fd) throws IOException
			CorruptObjectException {
