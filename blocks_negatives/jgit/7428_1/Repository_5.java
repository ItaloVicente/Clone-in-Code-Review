	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			doClose();
		}
	}

	/**
	 * Invoked when the use count drops to zero during {@link #close()}.
	 * <p>
	 * The default implementation closes the object and ref databases.
	 */
	protected void doClose() {
		getObjectDatabase().close();
		getRefDatabase().close();
	}

	public String toString() {
		String desc;
		if (getDirectory() != null)
			desc = getDirectory().getPath();
		else
			desc = getClass().getSimpleName() + "-"
					+ System.identityHashCode(this);
		return "Repository[" + desc + "]";
	}
