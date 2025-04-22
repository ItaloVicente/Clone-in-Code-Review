		if (streamTypeText != null) {
			b = new ByteArrayOutputStream();
			try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
					streamTypeText)) {
				out.write(outputBytes);
			}
			assertArrayEquals(expectedConversionBytes
