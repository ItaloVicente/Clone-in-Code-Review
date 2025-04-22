			if (showAllBranches) {
				for (Entry<String, Ref> refEntry : db.getRefDatabase()
						.getRefs(Constants.R_HEADS).entrySet()) {
					Ref ref = refEntry.getValue();
					if (ref.isSymbolic())
						continue;
					currentWalk.markStart(currentWalk.parseCommit(ref
							.getObjectId()));
				}
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
