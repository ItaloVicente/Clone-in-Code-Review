	 * Set reuse deltas configuration option for this writer. When enabled,
	 * writer will search for delta representation of object in repository and
	 * use it if possible. Normally, only deltas with base to another object
	 * existing in set of objects to pack will be used. Exception is however
	 * thin-pack (see
	 * {@link #preparePack(ProgressMonitor, Collection, Collection)} and
	 * {@link #preparePack(Iterator)}) where base object must exist on other
	 * side machine.
