		if (streamTypeText != null) {
			try (InputStream in = EolStreamTypeUtil.wrapInputStream(
					new ByteArrayInputStream(inputBytes)
				byte[] b = new byte[1024];
				int len = IO.readFully(in
				assertArrayEquals(expectedConversionBytes
						Arrays.copyOf(b
			}
