	private final void checkIfTooLarge(int typeCode
			throws IOException {
		if (0 < maxObjectSizeLimit && maxObjectSizeLimit < size)
			switch (typeCode) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				throw new TooLargeObjectInPackException(size

			case Constants.OBJ_OFS_DELTA:
			case Constants.OBJ_REF_DELTA:
				throw new TooLargeObjectInPackException(maxObjectSizeLimit);

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
	}

