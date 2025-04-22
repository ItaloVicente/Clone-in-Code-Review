		final Scheme scheme = (Scheme) object;
		int compareTo = Util.compare(this.id, scheme.id);
		if (compareTo == 0) {
			compareTo = Util.compare(this.name, scheme.name);
			if (compareTo == 0) {
				compareTo = Util.compare(this.parentId, scheme.parentId);
				if (compareTo == 0) {
					compareTo = Util.compare(this.description,
							scheme.description);
					if (compareTo == 0) {
						compareTo = Util.compare(this.defined, scheme.defined);
					}
				}
			}
		}

		return compareTo;
	}

	public final void define(final String name, final String description,
			final String parentId) {
		if (name == null) {
			throw new NullPointerException(
					"The name of a scheme cannot be null"); //$NON-NLS-1$
		}

		final boolean definedChanged = !this.defined;
		this.defined = true;
