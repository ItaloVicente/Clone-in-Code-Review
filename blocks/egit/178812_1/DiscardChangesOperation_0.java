	public void setRevision(String revision) {
		if (stage != null && revision != null) {
			throw new IllegalStateException(
					"Either stage or revision can be set, but not both"); //$NON-NLS-1$
		}
		this.revision = revision;
	}

