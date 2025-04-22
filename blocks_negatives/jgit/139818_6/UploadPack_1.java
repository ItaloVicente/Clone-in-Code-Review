	private boolean reportInternalServerErrorOverSideband() {
		try {
			@SuppressWarnings("resource" /* java 7 */)
			SideBandOutputStream err = new SideBandOutputStream(
					SideBandOutputStream.CH_ERROR,
					SideBandOutputStream.SMALL_BUF,
					rawOut);
			err.write(Constants.encode(JGitText.get().internalServerError));
			err.flush();
			return true;
		} catch (Throwable cannotReport) {
			return false;
		}
