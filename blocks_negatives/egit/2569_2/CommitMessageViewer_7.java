	/**
	 * @return List of heads from those current commit is reachable
	 */
	private List<Ref> getBranches() {
		RevWalk revWalk = new RevWalk(db);
		List<Ref> result = new ArrayList<Ref>();

		try {
			Map<String, Ref> refsMap = new HashMap<String, Ref>();
			refsMap.putAll(db.getRefDatabase().getRefs(Constants.R_HEADS));
			refsMap.putAll(db.getRefDatabase().getRefs(Constants.R_REMOTES));

			for (String headName : refsMap.keySet()) {
				RevCommit headCommit = revWalk.parseCommit(refsMap
						.get(headName).getObjectId());
				RevCommit base = revWalk.parseCommit(commit);

				if (revWalk.isMergedInto(base, headCommit))
			}
		} catch (IOException e) {
		}
		return result;
	}
