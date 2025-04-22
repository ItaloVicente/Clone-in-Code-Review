		@Test
		public void testNoNewlineAtEndAutoCRLF_true() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_d_crlf"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testNoNewlineAtEndAutoCRLF_false() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_d"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testNoNewlineAtEndAutoCRLF_input() throws Exception {
			try {
				db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_d"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_true() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_e_crlf"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_false() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_e"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testNoNewlineAtEndInHunkAutoCRLF_input() throws Exception {
			try {
				db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_e"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_true() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_add_nl_crlf"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_false() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_add_nl"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testAddNewlineAtEndAutoCRLF_input() throws Exception {
			try {
				db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_add_nl"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_true() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_last_rm_nl_crlf"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_false() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_last_rm_nl"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testRemoveNewlineAtEndAutoCRLF_input() throws Exception {
			try {
				db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
						null

				init("x_last_rm_nl"

				Result result = applyPatch();
				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

