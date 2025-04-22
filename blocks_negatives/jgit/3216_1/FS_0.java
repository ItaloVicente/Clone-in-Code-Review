			final IOException[] readException = new IOException[1];
			final String[] r = new String[1];

			/*
			 * Use a separate Thread so we don't wait forever for a result (a
			 * stalled pipe in the UI would be bad)
			 */
			final Thread piper = new Thread() {
				public void run() {
					Process p = null;
					try {
						p = Runtime.getRuntime().exec(command, null, dir);
						BufferedReader lineReader = new BufferedReader(
								new InputStreamReader(p.getInputStream(),
										encoding));
						try {
							r[0] = lineReader.readLine();
						} finally {
							p.getOutputStream().close();
							p.getErrorStream().close();
							lineReader.close();
						}
					} catch (IOException e) {
						readException[0] = e;
					}
				}
			};

			piper.start();
