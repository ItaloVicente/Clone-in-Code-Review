					if (!commit && squash) {
						mergeStatus = MergeStatus.MERGED_SQUASHED_NOT_COMMITTED;
					}
					if (!commit && !squash) {
						mergeStatus = MergeStatus.MERGED_NOT_COMMITTED;
					}
					if (commit && !squash) {
						newHeadId = new Git(getRepository()).commit()
								.setReflogComment(refLogMessage.toString())
								.call().getId();
