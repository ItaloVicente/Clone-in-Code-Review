			try (PostReceiveExecutor e = new PostReceiveExecutor()) {
				if (needPack()) {
					try {
						receivePackAndCheckConnectivity();
					} catch (IOException | RuntimeException
							| SubmoduleValidationException | Error err) {
						unlockPack();
						sendStatusReport(err);
						throw new UnpackException(err);
					}
