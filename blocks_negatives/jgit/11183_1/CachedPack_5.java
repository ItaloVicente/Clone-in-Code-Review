	/**
	 * Objects that start this pack.
	 * <p>
	 * All objects reachable from the tips are contained within this pack. If
	 * {@link PackWriter} is going to include everything reachable from all of
	 * these objects, this cached pack is eligible to be appended directly onto
	 * the output pack stream.
	 *
	 * @return the tip objects that describe this pack.
	 */
	public abstract Set<ObjectId> getTips();

