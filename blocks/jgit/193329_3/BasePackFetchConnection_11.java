
		if (capabilities.contains(GitProtocolConstants.CAPABILITY_SHALLOW)) {
			sendShallow(pckState);
		} else if (depth != null || deepenSince != null || !deepenNots.isEmpty()) {
			throw new PackProtocolException(JGitText.get().shallowNotSupported);
		}
