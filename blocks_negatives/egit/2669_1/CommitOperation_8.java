			final RefUpdate ru = repo.updateRef(Constants.HEAD);
			ru.setNewObjectId(commitId);
			ru.setRefLogMessage(buildReflogMessage(commitMessage), false);
			if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE) {
				throw new TeamException(NLS.bind(
						CoreText.CommitOperation_failedToUpdate, ru.getName(),
						commitId));
			}
		}
	}

	private void writeTreeWithSubTrees(Tree tree) throws TeamException {
		if (tree.getId() == null) {
			if (GitTraceLocation.CORE.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.CORE.getLocation(),
			try {
				for (TreeEntry entry : tree.members()) {
					if (entry.isModified()) {
						if (entry instanceof Tree) {
							writeTreeWithSubTrees((Tree) entry);
						} else {
							if (GitTraceLocation.CORE.isActive())
								GitTraceLocation.getTrace().trace(
										GitTraceLocation.CORE.getLocation(),
												+ entry.getFullName());
						}
					}
				}

				ObjectInserter inserter = tree.getRepository().newObjectInserter();
				try {
					tree.setId(inserter.insert(Constants.OBJ_TREE, tree.format()));
					inserter.flush();
				} finally {
					inserter.release();
				}
			} catch (IOException e) {
				throw new TeamException(
						CoreText.CommitOperation_errorWritingTrees, e);
