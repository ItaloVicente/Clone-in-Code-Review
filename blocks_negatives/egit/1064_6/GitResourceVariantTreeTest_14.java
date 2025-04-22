				.getContents();
		byte[] actualByte = new byte[actualIn.available()];
		actualIn.read(actualByte);
		InputStream expectedIn = ((IFile) mainJava.getResource()).getContents();
		byte[] expectedByte = new byte[expectedIn.available()];
		expectedIn.read(expectedByte);
