		if (info != null) {
			switch (info.type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				visit.data = inflateAndReturn(Source.DATABASE
				visit.id = oe;
				break;
			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
