	private void transmitOptions() throws IOException {
		for (final String pushOption : pushOptions) {
			pckOut.writeString(pushOption);
		}

		pckOut.end();
	}

