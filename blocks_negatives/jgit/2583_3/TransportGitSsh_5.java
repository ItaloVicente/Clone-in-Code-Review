	private class ExtConnection extends Connection {
		private Process proc;

		private int exitStatus;

		@Override
		void exec(String commandName) throws TransportException {
