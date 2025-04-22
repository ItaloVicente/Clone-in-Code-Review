	/**
	 * Allocate a new {@code PackWriter} state structure for an object.
	 * <p>
	 * {@link PackWriter} allocates these objects to keep track of the
	 * per-object state, and how to load the objects efficiently into the
	 * generated stream. Implementers may override this method to provide their
	 * own subclass with additional object state, such as to remember what file
	 * and position contains the object's data.
	 * <p>
	 * The default implementation of this object does not provide very efficient
	 * packing support; it inflates the object on the fly through {@code
	 * openObject} and deflates it again into the generated stream.
	 *
	 * @param obj
	 *            identity of the object that will be packed. The object's
	 *            parsed status is undefined here. Implementers must not rely on
	 *            the object being parsed.
	 * @return a new instance for this object.
	 */
	public ObjectToPack newObjectToPack(RevObject obj) {
		return new ObjectToPack(obj, obj.getType());
	}

