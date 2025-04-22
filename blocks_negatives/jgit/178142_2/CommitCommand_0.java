				if (headId != null && !allowEmpty.booleanValue()) {
					RevCommit headCommit = rw.parseCommit(headId);
					headCommit.getTree();
					if (indexTreeId.equals(headCommit.getTree())) {
						throw new EmptyCommitException(
								JGitText.get().emptyCommit);
					}
				}
