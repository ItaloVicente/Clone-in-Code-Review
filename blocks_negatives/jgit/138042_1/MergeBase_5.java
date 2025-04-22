	protected void run() throws Exception {
		for (RevCommit c : commits)
			argWalk.markStart(c);
		argWalk.setRevFilter(RevFilter.MERGE_BASE);
		int max = all ? Integer.MAX_VALUE : 1;
		while (max-- > 0) {
			final RevCommit b = argWalk.next();
			if (b == null)
				break;
			outw.println(b.getId().name());
