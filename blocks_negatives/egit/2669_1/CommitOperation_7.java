		for (java.util.Map.Entry<Repository, Tree> entry : treeMap.entrySet()) {
			Tree tree = entry.getValue();
			Repository repo = tree.getRepository();
			repo.getIndex().write();
			writeTreeWithSubTrees(tree);

			ObjectId currentHeadId = repo.resolve(Constants.HEAD);
			ObjectId[] parentIds;
			if (amending) {
				RevCommit[] parents = previousCommit.getParents();
				parentIds = new ObjectId[parents.length];
				for (int i = 0; i < parents.length; i++)
					parentIds[i] = parents[i].getId();
			} else {
				if (currentHeadId != null)
					parentIds = new ObjectId[] { currentHeadId };
				else
					parentIds = new ObjectId[0];
			}
			if (createChangeId) {
				ObjectId parentId;
				if (parentIds.length > 0)
					parentId = parentIds[0];
				else
					parentId = null;
				ObjectId changeId = ChangeIdUtil.computeChangeId(tree.getId(), parentId, authorIdent, committerIdent, commitMessage);
				commitMessage = ChangeIdUtil.insertId(commitMessage, changeId);
				if (changeId != null)
					commitMessage = commitMessage.replaceAll("\nChange-Id: I0000000000000000000000000000000000000000\n", "\nChange-Id: I" + changeId.getName() + "\n");  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
			CommitBuilder commit = new CommitBuilder();
			commit.setTreeId(tree.getTreeId());
			commit.setParentIds(parentIds);
			commit.setMessage(commitMessage);
			commit.setAuthor(new PersonIdent(authorIdent, commitDate,
							timeZone));
			commit.setCommitter(new PersonIdent(committerIdent, commitDate,
					timeZone));

			ObjectInserter inserter = repo.newObjectInserter();
			ObjectId commitId;
			try {
				commitId = inserter.insert(commit);
				inserter.flush();
			} finally {
				inserter.release();
			}
