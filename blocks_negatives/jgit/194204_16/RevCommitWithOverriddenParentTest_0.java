	@Test
	public void testOverriddenParent() throws Exception {
		RevCommit a = tr.commit().add("a", "foo").create();
		RevCommit b = tr.commit().parent(a).add("b", "bar").create();
		RevCommit c = tr.commit().parent(b).message("commit3").add("a", "foo'")
				.create();

		RevCommit cBar = new RevCommit(c.getId()) {
			@Override
			public int getParentCount() {
				return 1;
			}

			@Override
			public RevCommit getParent(int nth) {
				return a;
			}

			@Override
			public RevCommit[] getParents() {
				return new RevCommit[] { a };
			}

			@Override
			public boolean hasOverriddenParents() {
				return true;
			}
		};

		assertTrue(cBar.hasOverriddenParents());
		assertFalse(a.hasOverriddenParents());
		assertFalse(b.hasOverriddenParents());
		assertFalse(c.hasOverriddenParents());
	}
