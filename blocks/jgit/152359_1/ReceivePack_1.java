
	private class PostReceiveExecutor implements AutoCloseable {
		@Override
		public void close() {
			postReceive.onPostReceive(ReceivePack.this
					filterCommands(Result.OK));
		}
	}
