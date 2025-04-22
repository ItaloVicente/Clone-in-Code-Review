						if (!listenSock.isClosed()) {
							try {
								listenSock.close();
							} catch (IOException err) {
							}
						}
						listenSock = null;
