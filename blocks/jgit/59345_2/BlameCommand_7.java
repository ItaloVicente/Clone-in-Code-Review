		StreamType streamType = StreamConversionFactory.checkOutStreamType(
				getRepository()
		switch (streamType) {
		case TEXT_CRLF:
		case AUTO_CRLF:
			AutoLFInputStream in = new AutoLFInputStream(
