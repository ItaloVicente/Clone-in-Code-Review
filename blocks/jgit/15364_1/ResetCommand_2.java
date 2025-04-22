			final ObjectId commitTree;
			if (commitId != null)
				commitTree = parseCommit(commitId).getTree();
			else
				commitTree = null;

