		assertNotNull(actual);
		assertEquals(fileName, actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = getBytesAndCloseStream(actualIn);
		InputStream expectedIn = mainJava.getContents();
		byte[] expectedByte = getBytesAndCloseStream(expectedIn);
		assertArrayEquals(expectedByte, actualByte);
