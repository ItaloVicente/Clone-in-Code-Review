	private static ObjectLoader checkType(ObjectLoader ldr
			int typeHint) throws IncorrectObjectTypeException {
		if (typeHint != OBJ_ANY && ldr.getType() != typeHint) {
			throw new IncorrectObjectTypeException(id.copy()
		}
		return ldr;
	}

