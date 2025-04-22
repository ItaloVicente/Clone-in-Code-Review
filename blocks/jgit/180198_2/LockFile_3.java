					if (written) {
						throw new IOException(MessageFormat
								.format(JGitText.get().lockStreamClosed
					}
					if (out != null) {
						if (fsync) {
							os.getChannel().force(true);
						}
						out.close();
						os = null;
					}
					written = true;
