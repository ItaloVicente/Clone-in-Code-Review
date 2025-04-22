			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				break;
			default:
				throw new CorruptObjectException(id
						JGitText.get().corruptObjectInvalidType);
			}
