				while (isRunning()) {
					try {
						startClient(listenSock.accept());
					} catch (InterruptedIOException e) {
					} catch (IOException e) {
						break;
					}
				}

