	@Override
	public int hashCode() {
		return (contextId + getSourcePriority()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ContextActivation &&
			((ContextActivation) obj).getSourcePriority() == getSourcePriority() &&
 ((ContextActivation) obj).contextId.equals(contextId);
	}

