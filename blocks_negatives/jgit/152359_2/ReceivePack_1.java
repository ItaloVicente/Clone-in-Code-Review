			sendStatusReport(unpackError);

			if (unpackError != null) {
				try {
					postReceive.onPostReceive(this, filterCommands(Result.OK));
				} catch (Throwable e) {
				}
				throw new UnpackException(unpackError);
