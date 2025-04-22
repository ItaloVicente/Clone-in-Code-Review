	public RebaseCommand setUpstream(AnyObjectId upstream) {
		try {
			this.upstreamCommit = walk.parseCommit(upstream);
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().couldNotReadObjectWhileParsingCommit
					upstream.name())
		}
		return this;
	}

