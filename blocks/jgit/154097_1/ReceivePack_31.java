
	private class PostReceiveExecutor implements AutoCloseable {
		@Override
		public void close() {
			postReceive.onPostReceive(ReceivePack.this
					filterCommands(Result.OK));
		}
	}

	private class DefaultUnpackErrorHandler implements UnpackErrorHandler {
		@Override
		public void handleUnpackException(Throwable t) throws IOException {
			sendStatusReport(t);
		}
	}
