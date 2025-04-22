
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && (obj instanceof StashedCommitNode)
				&& index == ((StashedCommitNode) obj).getIndex();
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(index) * 31 + super.hashCode();
	}
