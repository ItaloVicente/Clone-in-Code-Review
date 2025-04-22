	private boolean prependNewline(File file) throws IOException {
		boolean prepend = false;
		long length = file.length();
		if (length > 0) {
			RandomAccessFile raf = new RandomAccessFile(file, "r"); //$NON-NLS-1$
			try {
				ByteBuffer buffer = ByteBuffer.allocate(1);
				FileChannel channel = raf.getChannel();
				channel.position(length - 1);
				if (channel.read(buffer) > 0) {
					buffer.rewind();
					prepend = buffer.get() != '\n';
				}
			} finally {
				raf.close();
			}
		}
		return prepend;
	}

	private String getEntry(File file, String entry) throws IOException {
		return prependNewline(file) ? "\n" + entry : entry; //$NON-NLS-1$
	}

