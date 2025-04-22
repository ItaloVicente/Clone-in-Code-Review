
		Set<ObjectId> shallowCommits = local.getObjectDatabase().getShallowCommits();
		if (capabilities.contains(GitProtocolConstants.CAPABILITY_SHALLOW)) {
			sendShallow(shallowCommits
		} else if (depth != null || deepenSince != null || !deepenNots.isEmpty()) {
			throw new PackProtocolException(JGitText.get().shallowNotSupported);
		}
