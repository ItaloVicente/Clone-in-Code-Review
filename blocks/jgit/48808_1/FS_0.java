	private static class GobblerThread extends Thread {
		private final Process p;
		private final String desc;
		private final String dir;
		private final boolean debug = LOG.isDebugEnabled();
		private final AtomicBoolean fail = new AtomicBoolean();

		private GobblerThread(Process p
			this.p = p;
			if (debug) {
				this.desc = Arrays.asList(command).toString();
				this.dir = dir.toString();
			} else {
				this.desc = null;
				this.dir = null;
			}
		}

		public void run() {
			InputStream is = p.getErrorStream();
			try {
				int ch;
				if (debug) {
					while ((ch = is.read()) != -1) {
						System.err.print((char) ch);
					}
				} else {
					while (is.read() != -1) {
					}
				}
			} catch (IOException e) {
				logError(e);
				fail.set(true);
			}
			try {
				is.close();
			} catch (IOException e) {
				logError(e);
				fail.set(true);
			}
		}

		private void logError(Throwable t) {
			if (!debug) {
				return;
			}
			String msg = MessageFormat.format(
					JGitText.get().exceptionCaughtDuringExcecutionOfCommand
			LOG.debug(msg
		}
	}

