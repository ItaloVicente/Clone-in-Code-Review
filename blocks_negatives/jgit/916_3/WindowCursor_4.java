
	/**
	 * @param curs cursor to release; may be null.
	 * @return always null.
	 */
	public static WindowCursor release(final WindowCursor curs) {
		if (curs != null)
			curs.release();
		return null;
	}
