	 * Returns <code>true</code> if this working set is capable of updating
	 * itself and reacting to changes in the state of its members. For
	 * non-aggregate working sets this means that the working set has an
	 * {@link IWorkingSetUpdater} installed while for aggregates it means that
	 * all component sets have {@link IWorkingSetUpdater}s installed. Otherwise
	 * returns <code>false</code>.
