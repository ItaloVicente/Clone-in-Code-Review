
		/**
		 * src = createBareRepository(); dst = createBareRepository();
		 *
		 * <Repository> d = new TestRepository<Repository>(dst); a =
		 * d.blob("a"); A = d.commit(d.tree(d.file("a", a))); B =
		 * d.commit().parent(A).create(); d.update(R_MASTER, B);
		 *
		 * new URIish(dst.getDirectory().getAbsolutePath()))) { t.fetch(PM,
		 * Collections.singleton(new RefSpec("+refs/*:refs/*")));
		 * assertEquals(B, src.resolve(R_MASTER)); }
		 *
		 * d.commit(d.tree(d.file("b", b)), A); d.update(R_PRIVATE, P);
		 */
