
	abstract ObjectId getTreeId();

	final protected boolean isOfTheSameType(NoteBucket other) {
		return this.getClass().equals(other.getClass());
	}
