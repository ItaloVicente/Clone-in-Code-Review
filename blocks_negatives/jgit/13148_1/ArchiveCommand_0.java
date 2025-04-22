		walk = new TreeWalk(repo);
	}

	/**
	 * Release any resources used by the internal ObjectReader.
	 * <p>
	 * This does not close the output stream set with setOutputStream, which
	 * belongs to the caller.
	 */
	public void release() {
		walk.release();
