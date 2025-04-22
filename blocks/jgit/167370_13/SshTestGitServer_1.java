
	private static class EchoCommand extends AbstractCommandSupport {

		protected EchoCommand(String command
				CloseableExecutorService executorService) {
			super(command
		}

		@Override
		public void run() {
			String[] parts = getCommand().split(" ");
			int timeout = 0;
			if (parts.length >= 2) {
				try {
					timeout = Integer.parseInt(parts[1]);
				} catch (NumberFormatException e) {
				}
				if (timeout > 0) {
					try {
						Thread.sleep(TimeUnit.SECONDS.toMillis(timeout));
					} catch (InterruptedException e) {
					}
				}
			}
			try {
				doEcho(getCommand()
				onExit(0);
			} catch (IOException e) {
				log.warn(
						MessageFormat.format("Could not run {0}"
						e);
				onExit(-1
			}
		}

		private void doEcho(String text
				throws IOException {
			stream.write(text.getBytes(StandardCharsets.UTF_8));
			stream.flush();
		}
	}
