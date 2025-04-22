			final AtomicBoolean gobblerFail = new AtomicBoolean(false);
			Thread gobbler = new Thread() {
				public void run() {
					InputStream is = p.getErrorStream();
					try {
						int ch;
						if (debug)
							while ((ch = is.read()) != -1)
								System.err.print((char) ch);
						else
							while (is.read() != -1) {
							}
					} catch (IOException e) {
						if (debug)
							e.printStackTrace(System.err);
						gobblerFail.set(true);
					}
					try {
						is.close();
					} catch (IOException e) {
						if (debug) {
							LOG.debug("Caught exception in gobbler thread", e); //$NON-NLS-1$
						}
						gobblerFail.set(true);
					}
				}
			};
