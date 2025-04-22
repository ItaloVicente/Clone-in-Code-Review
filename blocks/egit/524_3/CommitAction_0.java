			if (commitDialog.getCreateChangeId()) {
				ObjectId parentId;
				if (parentIds.length > 0)
					parentId = parentIds[0];
				else
					parentId = null;
				ObjectId changeId = ChangeIdUtil.computeChangeId(tree.getId(), parentId, authorIdent, committerIdent, commitMessage);
				commitMessage = ChangeIdUtil.insertId(commitMessage, changeId);
			}

