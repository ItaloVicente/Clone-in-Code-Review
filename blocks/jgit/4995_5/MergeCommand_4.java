				String mergeMessage = "";
				if (!squash) {
					mergeMessage = new MergeMessageFormatter().format(
							commits
					repo.writeMergeCommitMsg(mergeMessage);
					repo.writeMergeHeads(Arrays.asList(ref.getObjectId()));
				} else {
					List<RevCommit> revCommits = computeCommitsToSquash(
							commits
					String squashMessage = new SquashMessageFormatter().format(
							revCommits
					repo.writeSquashCommitMsg(squashMessage);
				}
