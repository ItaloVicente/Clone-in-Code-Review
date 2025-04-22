		try {
			if (commit != null) {
				RevCommit[] parents = commit.getParents();
				if (parents.length > 1)
					throw new IllegalStateException(
							CoreText.CreatePatchOperation_cannotCreatePatchForMergeCommit);

				ObjectId parentId;
				if (parents.length > 0)
					parentId = parents[0].getId();
				else
					parentId = null;
				List<DiffEntry> diffs = diffFmt.scan(parentId, commit.getId());
				for (DiffEntry ent : diffs) {
					String path;
					if (ChangeType.DELETE.equals(ent.getChangeType()))
						path = ent.getOldPath();
					else
						path = ent.getNewPath();
					currentEncoding = CompareCoreUtils.getResourceEncoding(repository, path);
					diffFmt.format(ent);
