	public static int typeCode(String typeString) {
		if (TYPE_BLOB.equals(typeString))
			return OBJ_BLOB;
		if (TYPE_TREE.equals(typeString))
			return OBJ_TREE;
		if (TYPE_COMMIT.equals(typeString))
			return OBJ_COMMIT;
		if (TYPE_TAG.equals(typeString))
			return OBJ_TAG;
		throw new IllegalArgumentException(MessageFormat.format(JGitText.get().badObjectType
	}

