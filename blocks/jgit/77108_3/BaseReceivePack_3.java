		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
			discardCommands();
			fatalError(msg);
			throw new PackProtocolException(msg);
		}
	}

	private void discardCommands() {
		if (sideBand) {
			long max = maxDiscardBytes;
			if (max < 0) {
				max = Math.max(3 * maxCommandBytes
			}
			try {
				new PacketLineIn(rawIn
			} catch (IOException e) {
			}
