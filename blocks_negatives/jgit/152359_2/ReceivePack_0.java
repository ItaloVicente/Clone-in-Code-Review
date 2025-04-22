			Throwable unpackError = null;
			if (needPack()) {
				try {
					receivePackAndCheckConnectivity();
				} catch (IOException | RuntimeException
						| SubmoduleValidationException | Error err) {
					unpackError = err;
