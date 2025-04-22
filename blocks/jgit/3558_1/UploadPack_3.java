		if (sideband) {
			try {
				sendPack(true);
			} catch (UploadPackMayNotContinueException noPack) {
				throw noPack;
			} catch (IOException err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			} catch (RuntimeException err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			} catch (Error err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			}
		} else {
			sendPack(false);
		}
	}

	private boolean reportInternalServerErrorOverSideband() {
		try {
			SideBandOutputStream err = new SideBandOutputStream(
					SideBandOutputStream.CH_ERROR
					SideBandOutputStream.SMALL_BUF
					rawOut);
			err.write(Constants.encode(JGitText.get().internalServerError));
			err.flush();
			return true;
		} catch (Throwable cannotReport) {
			return false;
		}
	}

	private void sendPack(final boolean sideband) throws IOException {
