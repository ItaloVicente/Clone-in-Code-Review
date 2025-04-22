			try {
				if (needPack()) {
					try {
						receivePackAndCheckConnectivity();
					} catch (IOException | RuntimeException | Error err) {
						unpackError = err;
					}
