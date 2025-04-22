	private void reportErrorDuringNegotiate(String msg) {
		try {
			pckOut.writeString("ERR " + msg + "\n");
		} catch (Throwable err) {
		}
	}

