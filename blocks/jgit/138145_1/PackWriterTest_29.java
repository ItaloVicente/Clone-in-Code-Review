		try (TestRepository<Repository> r = new TestRepository<>(repo)) {
			BranchBuilder bb = r.branch("refs/heads/master");
			contentA = r.blob("A");
			contentB = r.blob("B");
			contentC = r.blob("C");
			contentD = r.blob("D");
			contentE = r.blob("E");
			c1 = bb.commit().add("a"
			c2 = bb.commit().add("b"
			c3 = bb.commit().add("c"
			c4 = bb.commit().add("d"
			c5 = bb.commit().add("e"
			return repo;
		}
