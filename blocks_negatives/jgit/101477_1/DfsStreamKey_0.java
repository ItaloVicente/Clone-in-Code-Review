	/**
	 * Derive a new StreamKey based on this existing key.
	 *
	 * @param suffix
	 *            a derivation suffix.
	 * @return derived stream key.
	 */
	public abstract DfsStreamKey derive(String suffix);

