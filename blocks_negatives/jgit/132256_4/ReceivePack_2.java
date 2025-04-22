				if (echoCommandFailures && msgOut != null) {
					sendStatusReport(false, unpackError, new Reporter() {
						@Override
						void sendString(String s) throws IOException {
						}
					});
					msgOut.flush();
					try {
						Thread.sleep(500);
					} catch (InterruptedException wakeUp) {
					}
				}
