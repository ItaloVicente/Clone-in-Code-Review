		try (TestRepository<Repository> d = new TestRepository<>(dst)) {
			a = d.blob("a");
			A = d.commit(d.tree(d.file("a"
			B = d.commit().parent(A).create();
			d.update(R_MASTER

			try (Transport t = Transport.open(src
				t.fetch(PM
				assertEquals(B
			}
