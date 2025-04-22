						try (RevWalk revWalk = new RevWalk(repo)) {
							RevCommit headCommit = revWalk
									.parseCommit(headCommitId);
							RevTree headTree = headCommit.getTree();
							TreeWalk projectInRepo = TreeWalk.forPath(repo,
									repoRelativePath, headTree);
							if (projectInRepo != null) {
								treeItem.setChecked(true);
							}
