	private final long cas;
	private final T value;

	/**
	 * Construct a new CASValue with the given identifer and value.
	 *
	 * @param c the CAS identifier
	 * @param v the value
	 */
	public CASValue(long c, T v) {
		super();
		cas=c;
		value=v;
	}
