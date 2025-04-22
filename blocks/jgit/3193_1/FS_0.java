			final IOException[] readException = new IOException[1];
			final String[] r = new String[1];

			final Thread piper = new Thread() {
				public void run() {
					Process p = null;
					try {
						p = Runtime.getRuntime().exec(command
						BufferedReader lineReader = new BufferedReader(
								new InputStreamReader(p.getInputStream()
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
