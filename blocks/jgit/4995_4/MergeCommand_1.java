				String msg = null;
				ObjectId newHead
				MergeStatus mergeStatus = null;
				if (!squash) {
					updateHead(refLogMessage
					newHead = base = srcCommit;
					mergeStatus = MergeStatus.FAST_FORWARD;
				} else {
					msg = JGitText.get().squashCommitNotUpdatingHEAD;
					newHead = base = headId;
					mergeStatus = MergeStatus.FAST_FORWARD_SQUASHED;
				}
