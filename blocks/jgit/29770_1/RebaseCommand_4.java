	private List<RevCommit> calculatePickList(RevCommit headCommit)
			throws GitAPIException
		LogCommand cmd = new Git(repo).log().addRange(upstreamCommit
				headCommit);
		Iterable<RevCommit> commitsToUse = cmd.call();
		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();
		for (RevCommit commit : commitsToUse) {
			if (preserveMerges || commit.getParentCount() == 1)
				cherryPickList.add(commit);
		}
		Collections.reverse(cherryPickList);

		if (preserveMerges) {
			File rewrittenDir = rebaseState.getRewrittenDir();
			FileUtils.mkdir(rewrittenDir
			walk.reset();
			walk.setRevFilter(RevFilter.MERGE_BASE);
			walk.markStart(upstreamCommit);
			walk.markStart(headCommit);
			RevCommit base;
			while ((base = walk.next()) != null)
				RebaseState.createFile(rewrittenDir
						upstreamCommit.getName());

			Iterator<RevCommit> iterator = cherryPickList.iterator();
			pickLoop: while(iterator.hasNext()){
				RevCommit commit = iterator.next();
				for (int i = 0; i < commit.getParentCount(); i++) {
					boolean parentRewritten = new File(rewrittenDir
							.getParent(i).getName()).exists();
					if (parentRewritten) {
						new File(rewrittenDir
						continue pickLoop;
					}
				}
				iterator.remove();
			}
		}
		return cherryPickList;
	}

