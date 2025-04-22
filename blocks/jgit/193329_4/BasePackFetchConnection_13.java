		if (mayHaveShallow) {
			String line = handleShallowUnshallow(shallowCommits
			if (!PacketLineIn.isEnd(line)) {
				throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot
			}
		}

