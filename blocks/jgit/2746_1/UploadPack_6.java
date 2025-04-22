		try {
			preUploadHook.onEndNegotiateRound(this
					haveCnt
		} catch (UploadPackMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				pckOut.writeString("ERR " + fail.getMessage() + "\n");
				fail.setOutput();
			}
			throw fail;
		}

