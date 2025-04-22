
		if (GitProtocolConstants.SECTION_SHALLOW_INFO.equals(line)) {
			line = handleShallowUnshallow(shallowCommits
			if (!PacketLineIn.isDelimiter(line)) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().expectedGot
			}
			line = pckIn.readString();
		}

