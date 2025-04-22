	private void readPushOptions() throws IOException {
		String pushOption = pckIn.readString();

		while (pushOption != null) {
			pushOptions.add(pushOption);
		}
	}

