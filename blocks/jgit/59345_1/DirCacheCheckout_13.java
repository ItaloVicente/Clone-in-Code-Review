		StreamType streamType = StreamConversionFactory.checkOutStreamType(repo
				entry.getPathString()
		boolean convertCRLF = (streamType != StreamType.DIRECT);

		File tmpFile = File.createTempFile("._" + f.getName()
		try (OutputStream channel = StreamConversionFactory
				.checkOutStream(new FileOutputStream(tmpFile)
