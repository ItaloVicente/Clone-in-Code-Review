
	public int getDepth() {
		return depth;
	}

	public FetchCommand setDepth(int depth) {
		if ((depth <= 0) || (depth > Transport.DEPTH_INFINITE)) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidDepth
							Integer.valueOf(depth)));
		}
		this.depth = depth;
		return this;
	}

