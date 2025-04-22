		@Override
		void connect() throws TransportException {
		}

		@Override
		InputStream getInputStream() throws IOException {
			return proc.getInputStream();
		}

		@Override
		OutputStream getOutputStream() throws IOException {
			return proc.getOutputStream();
		}

		@Override
		InputStream getErrorStream() throws IOException {
			return proc.getErrorStream();
		}

		@Override
		int getExitStatus() {
			return exitStatus;
		}

		@Override
		void close() {
			if (proc != null) {
				try {
					try {
						exitStatus = proc.waitFor();
					} catch (InterruptedException e) {
					}
				} finally {
					proc = null;
				}
			}
