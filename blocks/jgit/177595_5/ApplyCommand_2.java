			applyBinary(repository
					null
		} else {
			if (convertCrLf) {
				try (InputStream input = EolStreamTypeUtil.wrapInputStream(
						new FileInputStream(f)
					raw = new RawText(IO.readWholeStream(input
				}
				checkOut = new CheckoutMetadata(EolStreamType.TEXT_CRLF
			} else {
				raw = new RawText(f);
				checkOut = new CheckoutMetadata(EolStreamType.DIRECT
			}
			applyText(repository
