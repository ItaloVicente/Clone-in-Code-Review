    /**
     * A factor for computing the hash code for all schemes.
     */
    private static final int HASH_FACTOR = 89;

    /**
     * The seed for the hash code for all schemes.
     */
    private static final int HASH_INITIAL = HandleObject.class.getName()
            .hashCode();

    /**
     * Whether this object is defined. A defined object is one that has been
     * fully initialized. By default, all objects start as undefined.
     */
    protected transient boolean defined = false;

    /**
     * The hash code for this object. This value is computed lazily, and marked
     * as invalid when one of the values on which it is based changes.
     */
    private transient int hashCode = HASH_CODE_NOT_COMPUTED;

    /**
     * The identifier for this object. This identifier should be unique across
     * all objects of the same type and should never change. This value will
     * never be <code>null</code>.
     */
    protected final String id;

    /**
     * The string representation of this object. This string is for debugging
     * purposes only, and is not meant to be displayed to the user. This value
     * is computed lazily, and is cleared if one of its dependent values
     * changes.
     */
    protected transient String string = null;

    /**
     * Constructs a new instance of <code>HandleObject</code>.
     *
     * @param id
     *            The id of this handle; must not be <code>null</code>.
     */
    protected HandleObject(final String id) {
        if (id == null) {
            throw new NullPointerException(
        }

        this.id = id;
    }

    /**
     * Tests whether this object is equal to another object. A handle object is
     * only equal to another handle object with the same id and the same class.
     *
     * @param object
     *            The object with which to compare; may be <code>null</code>.
     * @return <code>true</code> if the objects are equal; <code>false</code>
     *         otherwise.
     */
