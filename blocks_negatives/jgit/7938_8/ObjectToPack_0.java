	/**
	 * Construct for the specified object.
	 *
	 * @param obj
	 *            identity of the object that will be packed. The object's
	 *            parsed status is undefined here. Implementers must not rely on
	 *            the object being parsed.
	 */
	public ObjectToPack(RevObject obj) {
		this(obj, obj.getType());
	}

