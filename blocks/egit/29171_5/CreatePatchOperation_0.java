				ObjectId parentId;
				if (parents.length > 0)
					parentId = parents[0].getId();
				else
					parentId = null;
				List<DiffEntry> diffs = diffFmt.scan(parentId, commit.getId());
