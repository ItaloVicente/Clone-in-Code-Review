			@SuppressWarnings("resource" /* java 7 */)
			SideBandOutputStream err = new SideBandOutputStream(
					SideBandOutputStream.CH_ERROR,
					SideBandOutputStream.SMALL_BUF, requireNonNull(rawOut));
			err.write(Constants.encode(message));
			err.flush();
