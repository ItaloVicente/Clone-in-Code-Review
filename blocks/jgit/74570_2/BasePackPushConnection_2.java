	private void transmitOptions() throws IOException {
		if (optionStrings != null) {
			if (!capableOptionStrings && !optionStrings.isEmpty()) {
				throw new TransportException(uri
						MessageFormat.format(
								JGitText.get().optionStringsNotSupported
								optionStrings.toString()));
			}

			for (final String optionString : optionStrings) {
				pckOut.writeString(optionString);
			}
		}

		pckOut.end();
	}

