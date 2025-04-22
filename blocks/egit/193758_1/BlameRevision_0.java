		Attributes attributes = null;
		try {
			attributes = LfsFactory.getAttributesForPath(db, path);
		} catch (IOException e) {
			Activator.logWarning(
					"Failed to retrieve attributes for path: " + path, e); //$NON-NLS-1$
		}
		ObjectLoader oldLoader = reader.open(blobId.toObjectId(),
				Constants.OBJ_BLOB);
		ObjectLoader loader = oldLoader;
		if (attributes != null) {
			loader = LfsFactory.getInstance().applySmudgeFilter(db, oldLoader,
					attributes.get(Constants.ATTR_DIFF));
		}
		return new RawText(loader.getCachedBytes());
