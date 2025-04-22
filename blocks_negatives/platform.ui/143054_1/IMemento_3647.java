    /**
     * Special reserved key used to store the memento id
     * (value <code>"IMemento.internal.id"</code>).
     *
     * @see #getID()
     */

    /**
     * Creates a new child of this memento with the given type.
     * <p>
     * The <code>getChild</code> and <code>getChildren</code> methods
     * are used to retrieve children of a given type.
     * </p>
     *
     * @param type the type
     * @return a new child memento
     * @see #getChild
     * @see #getChildren
     */
    IMemento createChild(String type);

    /**
     * Creates a new child of this memento with the given type and id.
     * The id is stored in the child memento (using a special reserved
     * key, <code>TAG_ID</code>) and can be retrieved using <code>getId</code>.
     * <p>
     * The <code>getChild</code> and <code>getChildren</code> methods
     * are used to retrieve children of a given type.
     * </p>
     *
     * @param type the type
     * @param id the child id
     * @return a new child memento with the given type and id
     * @see #getID
     */
    IMemento createChild(String type, String id);

    /**
