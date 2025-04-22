		RevCommit Z;
		try (TestRepository<Repository> tr = new TestRepository<>(
				remoteRepository)) {
			b = tr.branch(master);
			Z = b.commit().message("Z").create();
		}
