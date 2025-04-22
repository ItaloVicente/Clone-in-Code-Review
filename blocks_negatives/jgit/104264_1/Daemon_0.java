		final ServerSocket listenSock = new ServerSocket(
				myAddress != null ? myAddress.getPort() : 0, BACKLOG,
				myAddress != null ? myAddress.getAddress() : null);
		myAddress = (InetSocketAddress) listenSock.getLocalSocketAddress();

		run = true;
		acceptThread = new Thread(processors, "Git-Daemon-Accept") { //$NON-NLS-1$
			@Override
			public void run() {
				while (isRunning()) {
					try {
						startClient(listenSock.accept());
					} catch (InterruptedIOException e) {
					} catch (IOException e) {
						break;
					}
				}

				try {
					listenSock.close();
				} catch (IOException err) {
				} finally {
					synchronized (Daemon.this) {
						acceptThread = null;
					}
				}
			}
		};
