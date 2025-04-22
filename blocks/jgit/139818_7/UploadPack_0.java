	private void reportInternalServerErrorOverSideband() throws IOException {
		SideBandOutputStream err = new SideBandOutputStream(
				SideBandOutputStream.CH_ERROR
				rawOut);
		err.write(Constants.encode(JGitText.get().internalServerError));
		err.flush();
