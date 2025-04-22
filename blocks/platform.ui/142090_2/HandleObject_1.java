	private static final int HASH_FACTOR = 89;

	private static final int HASH_INITIAL = HandleObject.class.getName()
			.hashCode();

	protected transient boolean defined = false;

	private transient int hashCode = HASH_CODE_NOT_COMPUTED;

	protected final String id;

	protected transient String string = null;

	protected HandleObject(final String id) {
		if (id == null) {
			throw new NullPointerException(
					"Cannot create a handle with a null id"); //$NON-NLS-1$
		}

		this.id = id;
	}

