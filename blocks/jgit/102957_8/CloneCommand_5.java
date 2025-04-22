
	public int getDepth() {
		return depth;
	}

	public CloneCommand setDepth(int depth) {
		if ((depth <= 0) || (depth > Transport.DEPTH_INFINITE)) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidDepth
							Integer.valueOf(depth)));
		}
		else if (depth != Transport.DEPTH_INFINITE) {
			this.setCloneAllBranches(false);
			this.setCloneSubmodules(false);
		}
		this.depth = depth;
		return this;
	}

