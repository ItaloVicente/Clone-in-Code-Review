		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes),
				streamTypeText)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in, b, 0);
			assertArrayEquals(expectedConversionBytes, Arrays.copyOf(b, len));
