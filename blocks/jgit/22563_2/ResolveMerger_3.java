	private static boolean isBinaryEntry(Repository repository
			CanonicalTreeParser tree) throws IOException {
		if (tree != null && !tree.getEntryObjectId().equals(ObjectId.zeroId()))
			return RawText.isBinary(repository.open(tree.getEntryObjectId()
					Constants.OBJ_BLOB).getCachedBytes());
		return false;
	}

