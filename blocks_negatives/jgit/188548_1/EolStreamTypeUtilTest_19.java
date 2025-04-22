		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes),
				streamTypeWithBinaryCheck)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in, b, 0);
			assertArrayEquals(inputBytes, Arrays.copyOf(b, len));
