				String msg = null;
				ObjectId newHead
				EnumSet<MergeStatus> status = EnumSet
						.of(MergeStatus.FAST_FORWARD);
				if (!squash) {
					updateHead(refLogMessage
					newHead = base = srcCommit;
				} else {
					msg = JGitText.get().squashCommitNotUpdatingHEAD;
					newHead = base = headId;
					status.add(MergeStatus.SQUASHED);
				}
