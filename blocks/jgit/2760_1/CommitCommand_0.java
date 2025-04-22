	private void insertChangeId(ObjectId treeId) throws IOException {
		ObjectId firstParentId = null;
		if (!parents.isEmpty())
			firstParentId = parents.get(0);
		ObjectId changeId = ChangeIdUtil.computeChangeId(treeId
				author
		message = ChangeIdUtil.insertId(message
		if (changeId != null)
			message = message.replaceAll("\nChange-Id: I"
					+ ObjectId.zeroId().getName() + "\n"
					+ changeId.getName() + "\n");
	}

