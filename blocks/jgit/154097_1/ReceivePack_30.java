			try (PostReceiveExecutor e = new PostReceiveExecutor()) {
				if (needPack()) {
					try {
						receivePackAndCheckConnectivity();
					} catch (IOException | RuntimeException
							| SubmoduleValidationException | Error err) {
						unlockPack();
						unpackErrorHandler.handleUnpackException(err);
						throw new UnpackException(err);
					}
