			Throwable unpackError = null;
			if (needPack()) {
				try {
					receivePackAndCheckConnectivity();
				} catch (IOException | RuntimeException | Error err) {
					unpackError = err;
