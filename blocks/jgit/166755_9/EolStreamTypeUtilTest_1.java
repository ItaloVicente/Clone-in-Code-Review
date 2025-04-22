		if (streamTypeWithBinaryCheck != null) {
			b = new ByteArrayOutputStream();
			try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
					streamTypeWithBinaryCheck)) {
				out.write(outputBytes);
			}
			assertArrayEquals(expectedConversionBytes
