		if (maxPackSizeLimit >= 0)
			rawIn = new LimitedInputStream(rawIn, maxPackSizeLimit) {
				@Override
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};

