		assertArrayEquals(expectedConversionBytes, b.toByteArray());

		b = new ByteArrayOutputStream();
		try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b,
				streamTypeWithBinaryCheck)) {
			out.write(outputBytes);
