	private void fillBranches() {
		Repository repository = getCommit().getRepository();
		RevCommit commit = getCommit().getRevCommit();
		RevWalk revWalk = new RevWalk(repository);
		List<Ref> result = new ArrayList<Ref>();
		try {
			Map<String, Ref> refsMap = new HashMap<String, Ref>();
			refsMap.putAll(repository.getRefDatabase().getRefs(
					Constants.R_HEADS));
			refsMap.putAll(repository.getRefDatabase().getRefs(
					Constants.R_REMOTES));
			for (Ref ref : refsMap.values()) {
				if (ref.isSymbolic())
					continue;
				RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());
				RevCommit base = revWalk.parseCommit(commit);
				if (revWalk.isMergedInto(base, headCommit))
					result.add(ref);
			}
		} catch (IOException ignored) {
		}

