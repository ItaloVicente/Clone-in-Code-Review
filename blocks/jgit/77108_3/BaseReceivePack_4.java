	private InputStream packInputStream() {
		InputStream packIn = rawIn;
		if (maxPackSizeLimit >= 0) {
			packIn = new LimitedInputStream(packIn
				@Override
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};
		}
		return packIn;
	}

