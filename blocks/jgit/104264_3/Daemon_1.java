	private class Acceptor extends Thread {

		private final ServerSocket listenSocket;

		private final AtomicBoolean running = new AtomicBoolean(true);

		public Acceptor(ThreadGroup group
			super(group
			this.listenSocket = socket;
		}

		@Override
		public void run() {
			setUncaughtExceptionHandler((thread
			while (isRunning()) {
				try {
					startClient(listenSocket.accept());
				} catch (SocketException e) {
				} catch (IOException e) {
					break;
				}
			}

			terminate();
		}

		private void terminate() {
			try {
				shutDown();
			} finally {
				clearThread();
			}
		}

		public boolean isRunning() {
			return running.get();
		}

		public void shutDown() {
			running.set(false);
			try {
				listenSocket.close();
			} catch (IOException err) {
			}
		}

	}

