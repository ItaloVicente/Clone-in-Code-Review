
	private class GitReceivePackCommand extends AbstractCommandSupport {

		protected GitReceivePackCommand(String command
				ExecutorService executorService) {
			super(command
		}

		@Override
		public void run() {
			try {
				new ReceivePack(repository).receive(getInputStream()
						getOutputStream()
				onExit(0);
			} catch (IOException e) {
				log.warn(
						MessageFormat.format("Could not run {0}"
						e);
				onExit(-1
			}
		}

	}
