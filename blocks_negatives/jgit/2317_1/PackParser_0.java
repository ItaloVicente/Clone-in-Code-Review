		switch (info.type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			visit.data = inflateAndReturn(Source.DATABASE, info.size);
			visit.id = oe;
			break;
		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType, info.type));
		}
