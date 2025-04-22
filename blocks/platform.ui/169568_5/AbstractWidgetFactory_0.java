	public final W create() {
		if (this.parent == null) {
			throw new UnsupportedOperationException(
					"parent() must be called before this call or create(parent) must be used "); //$NON-NLS-1$
		}
		return create(this.parent);
	}

	public F parent(P parent) {
		this.parent = parent;
		return cast(this);
	}

