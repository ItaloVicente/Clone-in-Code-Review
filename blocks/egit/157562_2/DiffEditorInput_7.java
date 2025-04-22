		if (adapter == IRepositoryCommit.class) {
			return adapter.cast(tip);
		} else if (adapter == RevCommit.class) {
			return adapter.cast(tip.getRevCommit());
		} else if (adapter == Repository.class) {
			return adapter.cast(tip.getRepository());
		}
