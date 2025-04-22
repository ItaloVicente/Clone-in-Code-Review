
	public final Integer getDepth() {
		return depth;
	}

	public final void setDepth(int depth) {
		if (depth < 1) {
			throw new IllegalArgumentException(JGitText.get().depthMustBeAt1);
		}
		this.depth = Integer.valueOf(depth);
	}

	public final void setDepth(Integer depth) {
		if (depth != null && depth.intValue() < 1) {
			throw new IllegalArgumentException(JGitText.get().depthMustBeAt1);
		}
		this.depth = depth;
	}

	public final Instant getDeepenSince() {
		return deepenSince;
	}

	public final void setDeepenSince(@NonNull Instant deepenSince) {
		this.deepenSince = deepenSince;
	}

	public final List<String> getDeepenNots() {
		return deepenNots;
	}

	public final void setDeepenNots(@NonNull List<String> deepenNots) {
		this.deepenNots = deepenNots;
	}

