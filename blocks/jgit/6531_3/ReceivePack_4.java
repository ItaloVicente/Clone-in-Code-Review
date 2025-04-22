				if (echoCommandFailures && msgOut != null) {
					sendStatusReport(false
						void sendString(final String s) throws IOException {
							msgOut.write(Constants.encode(s + "\n"));
						}
					});
					msgOut.flush();
					try {
						Thread.sleep(500);
					} catch (InterruptedException wakeUp) {
					}
				}
