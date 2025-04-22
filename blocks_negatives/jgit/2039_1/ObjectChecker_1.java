	public void check(final int objType, final byte[] raw)
			throws CorruptObjectException {
		switch (objType) {
		case Constants.OBJ_COMMIT:
			checkCommit(raw);
			break;
		case Constants.OBJ_TAG:
			checkTag(raw);
			break;
		case Constants.OBJ_TREE:
			checkTree(raw);
			break;
		case Constants.OBJ_BLOB:
			checkBlob(raw);
			break;
		default:
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().corruptObjectInvalidType2, objType));
		}
