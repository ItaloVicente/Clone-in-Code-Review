		private void insertChangeId(org.eclipse.jgit.lib.CommitBuilder c)
				throws IOException {
			ObjectId firstParentId = null;
			if (!parents.isEmpty())
				firstParentId = parents.get(0);
			ObjectId changeId = ChangeIdUtil.computeChangeId(c.getTreeId()
					firstParentId
			message = ChangeIdUtil.insertId(message
			if (changeId != null)
						+ ObjectId.zeroId().getName() + "\n"
		}

