
		@Test
		public void testNoNewlineAtEnd() throws Exception {
			init("x_d"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndInHunk() throws Exception {
			init("x_e"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testAddNewlineAtEnd() throws Exception {
			init("x_add_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testRemoveNewlineAtEnd() throws Exception {
			init("x_last_rm_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndAutoCRLF_true() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_d"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndAutoCRLF_false() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_d"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndAutoCRLF_input() throws Exception {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_d"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_true() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_e"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_false() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_e"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_input() throws Exception {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_e"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_true() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_add_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_false() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_add_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_input() throws Exception {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_add_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_true() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_last_rm_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_false() throws Exception {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_last_rm_nl"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_input() throws Exception {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					null

			init("x_last_rm_nl"

			Result result = applyPatch();
			verifyChange(result
		}
