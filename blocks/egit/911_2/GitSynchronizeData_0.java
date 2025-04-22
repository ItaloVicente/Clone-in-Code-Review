			if (tag != null) {
				Commit commit = repo.mapCommit(tag.getObjId());
				if (commit != null)
					return commit.getTree();
			}
			return null;
