		}

		@Override
		InputStream getInputStream() throws IOException {
			return channel.getInputStream();
		}

		@Override
		OutputStream getOutputStream() throws IOException {
			final OutputStream out = channel.getOutputStream();
			if (getTimeout() <= 0)
				return out;
			final PipedInputStream pipeIn = new PipedInputStream();
			final StreamCopyThread copier = new StreamCopyThread(pipeIn
			final PipedOutputStream pipeOut = new PipedOutputStream(pipeIn) {
				@Override
				public void flush() throws IOException {
					super.flush();
					copier.flush();
				}

				@Override
				public void close() throws IOException {
					super.close();
					try {
						copier.join(getTimeout() * 1000);
					} catch (InterruptedException e) {
					}
				}
			};
			copier.start();
			return pipeOut;
		}

		@Override
		InputStream getErrorStream() throws IOException {
			return channel.getErrStream();
		}

		@Override
		int getExitStatus() {
			return exitStatus;
		}
