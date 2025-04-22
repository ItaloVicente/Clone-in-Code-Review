				if (!background) {
					try (BufferedReader reader = Files
							.newBufferedReader(FileUtils.toPath(logFile))) {
						char[] buf = new char[1000];
						int len = reader.read(buf, 0, 1000);
						String oldError = new String(buf, 0, len);

						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().gcLogExists, oldError, logFile));
					}
				}
