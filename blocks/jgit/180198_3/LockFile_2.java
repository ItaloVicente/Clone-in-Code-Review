
			private OutputStream out;

			private boolean closed;

			private OutputStream get() throws IOException {
				if (written) {
					throw new IOException(MessageFormat
							.format(JGitText.get().lockStreamMultiple
				}
				if (out == null) {
					os = getStream();
					if (fsync) {
						out = Channels.newOutputStream(os.getChannel());
					} else {
						out = os;
					}
				}
				return out;
			}

