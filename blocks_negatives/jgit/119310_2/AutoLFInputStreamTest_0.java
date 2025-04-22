		final InputStream bis1 = new ByteArrayInputStream(input);
		final InputStream cis1 = new AutoLFInputStream(bis1, detectBinary);
		int index1 = 0;
		for (int b = cis1.read(); b != -1; b = cis1.read()) {
			assertEquals(expected[index1], (byte) b);
			index1++;
		}

		assertEquals(expected.length, index1);

		for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
			final byte[] buffer = new byte[bufferSize];
			final InputStream bis2 = new ByteArrayInputStream(input);
			final InputStream cis2 = new AutoLFInputStream(bis2, detectBinary);
