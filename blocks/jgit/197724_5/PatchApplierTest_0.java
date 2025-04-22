
		@Test
		public void testNoNewlineAtEnd() throws Exception {
			init("x_d");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndInHunk() throws Exception {
			init("x_e");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testAddNewlineAtEnd() throws Exception {
			init("x_add_nl");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testRemoveNewlineAtEnd() throws Exception {
			init("x_last_rm_nl");

			Result result = applyPatch();
			verifyChange(result
		}
