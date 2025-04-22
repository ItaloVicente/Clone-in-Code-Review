
	/**
	 * @return raw object type from object header, as stored in storage (pack,
	 *         loose file). This may be different from {@link #getType()} result
	 *         for packs (see {@link Constants}).
	 */
	public abstract int getRawType();

	/**
	 * @return raw size of object from object header (pack, loose file).
	 *         Interpretation of this value depends on {@link #getRawType()}.
	 */
	public abstract long getRawSize();
