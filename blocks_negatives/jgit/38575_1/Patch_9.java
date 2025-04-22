		final TemporaryBuffer b = new TemporaryBuffer.LocalFile();
		try {
			b.copy(is);
			b.close();
			return b.toByteArray();
		} finally {
			b.destroy();
		}
