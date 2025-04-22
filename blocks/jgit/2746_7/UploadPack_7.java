		try {
			if (wantAll.isEmpty()) {
				preUploadHook.onSendPack(this
			} else {
				preUploadHook.onSendPack(this
			}
		} catch (UploadPackMayNotContinueException noPack) {
			if (sideband && noPack.getMessage() != null) {
				noPack.setOutput();
				SideBandOutputStream err = new SideBandOutputStream(
						SideBandOutputStream.CH_ERROR
						SideBandOutputStream.SMALL_BUF
				err.write(Constants.encode(noPack.getMessage()));
				err.flush();
			}
			throw noPack;
		}

