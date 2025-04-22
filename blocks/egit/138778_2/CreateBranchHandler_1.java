	private List<Ref> getBranches(IRepositoryCommit commit) {
		RevCommit c = commit.getRevCommit();
		if (!(c instanceof PlotCommit)) {
			return Collections.emptyList();
		}
		PlotCommit p = (PlotCommit) c;

		List<Ref> result = new ArrayList<>();
		int refCount = p.getRefCount();
		for (int i = 0; i < refCount; i++) {
			Ref ref = p.getRef(i);
			String refName = ref.getName();
			if (refName.startsWith(Constants.R_HEADS)
					|| refName.startsWith(Constants.R_REMOTES))
				result.add(ref);
		}
		return result;
	}

