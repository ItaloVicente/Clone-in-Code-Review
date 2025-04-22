		if (biDirectionalPipe) {
			pckOut.end();
		} else {
			shouldFlushShallowResponse = true;
		}
	}

	void onParseNegotiateRequestDone() throws IOException {
		if (receivedNegotiateRequest) {
			return;
		}
		receivedNegotiateRequest = true;
		if (biDirectionalPipe) {
			return;
		}

		int eof = rawIn.read();
		if (0 <= eof)
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().expectedEOFReceived

		if (shouldFlushShallowResponse) {
			for (String line : shallowResponseBuffer) {
				pckOut.writeString(line);
			}
			pckOut.end();
			shouldFlushShallowResponse = false;
			shallowResponseBuffer.clear();
		}
