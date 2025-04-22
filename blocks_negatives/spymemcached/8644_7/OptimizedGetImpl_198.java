	/**
	 * Add a new GetOperation to get.
	 */
	public void addOperation(GetOperation o) {
		getKeys().addAll(o.getKeys());
		pcb.addCallbacks(o);
	}
