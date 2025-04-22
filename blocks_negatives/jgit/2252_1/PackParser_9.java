	public void setFixThin(final boolean fix) {
		fixThin = fix;
	}

	/**
	 * Configure this index pack instance to keep an empty pack.
	 * <p>
	 * By default an empty pack (a pack with no objects) is not kept, as doing
	 * so is completely pointless. With no objects in the pack there is no data
	 * stored by it, so the pack is unnecessary.
	 *
	 * @param empty true to enable keeping an empty pack.
	 */
	public void setKeepEmpty(final boolean empty) {
		keepEmpty = empty;
