	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		if (depth < 0) {
			throw new IllegalArgumentException(
					JGitText.get().invalidDepth);
		}
		this.depth = depth;
	}

