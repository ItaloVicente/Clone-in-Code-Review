			return exec(command, null, timeout);
		}

		@Override
		public Process exec(String command, Map<String, String> environment,
				int timeout) throws TransportException {
