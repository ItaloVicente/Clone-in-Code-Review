
	private class DefaultUnpackErrorHandler implements UnpackErrorHandler {
		@Override
		public void handleUnpackException(Throwable t) throws IOException {
			sendStatusReport(t);
		}
	}
