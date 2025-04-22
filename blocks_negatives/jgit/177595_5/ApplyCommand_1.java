		if (convertCrLf) {
			try (InputStream input = EolStreamTypeUtil.wrapInputStream(
					new FileInputStream(f), EolStreamType.TEXT_LF)) {
				raw = new RawText(IO.readWholeStream(input, 0).array());
			}
			checkOut = new CheckoutMetadata(EolStreamType.TEXT_CRLF, null);
		} else {
			raw = new RawText(f);
