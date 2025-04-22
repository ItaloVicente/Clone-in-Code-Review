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
