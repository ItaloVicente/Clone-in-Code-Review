		try {
			preUploadHook.onEndNegotiateRound(this, wantAll, //
					haveCnt, missCnt, sentReady);
		} catch (UploadPackMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				pckOut.writeString("ERR " + fail.getMessage() + "\n");
				fail.setOutput();
			}
			throw fail;
		}

