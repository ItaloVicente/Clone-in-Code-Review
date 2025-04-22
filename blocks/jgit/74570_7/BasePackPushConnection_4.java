	private void transmitOptions() throws IOException {
		if (pushOptions != null) {
			for (final String pushOption : pushOptions) {
				pckOut.writeString(pushOption);
			}

			pckOut.end();
		}
	}

