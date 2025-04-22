		try (TestRepository<Repository> db_t = new TestRepository<>(db)) {
			BranchBuilder master = db_t.branch("master");
			RevCommit m0 = master.commit()
					.add("f"
					.create();
			RevCommit m1 = master.commit()
					.add("f"
					.message("m1").create();
			db_t.getRevWalk().parseCommit(m1);

			BranchBuilder side = db_t.branch("side");
			RevCommit s1 = side.commit().parent(m0)
					.add("f"
					.create();
			RevCommit s2 = side.commit().parent(m1)
					.add("f"
							"1-master\n2\n3\n4\n5\n6\n7-res(side)\n8\n9-side\n")
					.message("s2(merge)").create();
			master.commit().parent(s1)
					.add("f"
							"1-master\n2\n3\n4\n5\n6\n7-conflict\n8\n9-side\n")
					.message("m2(merge)").create();

			Git git = Git.wrap(db);
			git.checkout().setName("master").call();

			MergeResult result = git.merge()
					.setStrategy(MergeStrategy.RECURSIVE).include("side"
					.call();
			assertEquals(MergeStatus.CONFLICTING
		}
