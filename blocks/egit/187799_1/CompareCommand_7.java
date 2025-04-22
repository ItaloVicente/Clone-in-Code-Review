	private RevCommit getCommit(Repository repository,
			RepositoryTreeNode<?> node) throws IOException {
		if (node instanceof TagNode) {
			String oid = ((TagNode) node).getCommitId();
			if (oid == null) {
				return null;
			}
			return repository.parseCommit(ObjectId.fromString(oid));
		} else if (node instanceof RefNode
				|| node instanceof AdditionalRefNode) {
			Ref ref = (Ref) node.getObject();
			ref = repository.exactRef(ref.getName());
			if (ref != null) {
				return repository.parseCommit(ref.getObjectId());
			}
		}
		return null;
	}

