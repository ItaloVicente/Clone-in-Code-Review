
				StreamType streamType = StreamConversionFactory
						.checkInStreamType(repository, gitPath,
						FileMode.REGULAR_FILE);
				switch (streamType) {
				case DIRECT:
