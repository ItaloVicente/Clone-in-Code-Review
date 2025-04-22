
	public int getDepth() {
		return depth;
	}

	public FetchCommand setDepth(int depth) {
		if (depth <= 0) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidDepth
							Integer.valueOf(depth)));
		}
		this.depth = depth;
		return this;
	}

