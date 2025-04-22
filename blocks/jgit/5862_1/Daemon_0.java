					while (isRunning()) {
						try {
							startClient(listenSock.accept());
						} catch (SocketException e) {
						} catch (IOException e) {
							break;
						}
					}
