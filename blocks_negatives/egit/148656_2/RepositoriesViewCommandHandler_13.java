		Repository repo = treeNode.getRepository();
		try {
			Ref ref = repo.exactRef(Constants.HEAD);
			return ref != null && ref.getObjectId() != null;
		} catch (IOException e) {
			return false;
		}
