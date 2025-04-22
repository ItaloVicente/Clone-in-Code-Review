	private void assertMagic(long offset
		BufferedInputStream in = new BufferedInputStream(
				new FileInputStream(file));
		try {
			in.skip(offset);

			byte[] actual = new byte[magicBytes.length];
			in.read(actual);
			assertArrayEquals(magicBytes
		} finally {
			in.close();
		}
	}

	private void assertMagic(byte[] magicBytes
		assertMagic(0
	}

	private void assertIsTar(File file) throws Exception {
		assertMagic(257
	}

	private void assertIsZip(File file) throws Exception {
		assertMagic(new byte[] { 'P'
	}

	private void assertIsGzip(File file) throws Exception {
		assertMagic(new byte[] { 037
	}

	private void assertIsBzip2(File file) throws Exception {
		assertMagic(new byte[] { 'B'
	}

	private void assertIsXz(File file) throws Exception {
		assertMagic(new byte[] { (byte) 0xfd
	}

