		private void setupStreams() throws IOException {
			inputStream = channel.getInputStream();

			final OutputStream out = channel.getOutputStream();
			if (timeout <= 0) {
				outputStream = out;
			} else {
				final PipedInputStream pipeIn = new PipedInputStream();
				final StreamCopyThread copier = new StreamCopyThread(pipeIn
						out);
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
							copier.join(timeout * 1000);
						} catch (InterruptedException e) {
						}
					}
				};
				copier.start();
				outputStream = pipeOut;
			}

			errStream = channel.getErrStream();
		}

