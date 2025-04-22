		if (maxPackSizeLimit > 0)
			rawIn = new LimitedInputStream(rawIn
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};

