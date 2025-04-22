				walkWorkingTree(repository, commits, pattern, monitor);
			}
		}
	}

	private void walkWorkingTree(Repository repository, List<RevCommit> commits,
			Pattern pattern, IProgressMonitor monitor)
			throws MissingObjectException, IncorrectObjectTypeException,
			CorruptObjectException, IOException {
		String repositoryName=repository.getWorkTree().getName();
		List<Ref> refs = repository.getRefDatabase().getRefs();
		for (RevCommit commit : commits) {
			Optional<Ref> ref = refs.stream()
					.filter(r -> commit.getId().equals(r.getObjectId()))
					.findFirst();
			if (ref.isPresent()) {
				String branchName = Repository
						.shortenRefName(ref.get().getTarget().getName());
				try (RevWalk walk = new RevWalk(repository);
						TreeWalk treeWalk = new TreeWalk(repository,
						walk.getObjectReader())) {
					treeWalk.addTree(commit.getTree());
					while (treeWalk.next()) {
						if (monitor.isCanceled()) {
							throw new OperationCanceledException();
						} else if (treeWalk.isSubtree()) {
							treeWalk.enterSubtree();
						} else {
							ObjectLoader loader = walk.getObjectReader()
									.open(treeWalk.getObjectId(0));
							String content = new String(
									loader.getCachedBytes());
							if (pattern.matcher(content).find()) {
								System.out.println(String.format("%s [%s] - %s", //$NON-NLS-1$
										repositoryName, branchName,
										treeWalk.getPathString()));
							}
						}
					}
				}
