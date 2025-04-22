
		long timeof(RevObject o) {
			if (o instanceof RevCommit)
				return ((RevCommit) o).getCommitTime();
			if (o instanceof RevTag) {
				RevTag tag = (RevTag) o;
				PersonIdent who = tag.getTaggerIdent();
				return who != null ? who.getWhen().getTime() : 0;
			}
