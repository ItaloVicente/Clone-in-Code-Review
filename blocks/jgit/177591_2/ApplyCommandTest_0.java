	@Test
	public void testCrLf() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = init("crlf"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfOff() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = init("crlf"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfEmptyCommitted() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = init("crlf3"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfNewFile() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = init("crlf4"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testPatchWithCrLf() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = init("crlf2"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testPatchWithCrLf2() throws Exception {
		String name = "crlf2";
		try (Git git = new Git(db)) {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			a = new RawText(readFile(name + "_PreImage"));
			write(new File(db.getWorkTree()
					a.getString(0

			git.add().addFilepattern(name).call();
			git.commit().setMessage("PreImage").call();

			b = new RawText(readFile(name + "_PostImage"));

			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			ApplyResult result = git.apply()
					.setPatch(getTestResource(name + ".patch")).call();
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	private static class ReplaceFilter extends FilterCommand {

		private final char toReplace;

		private final char replacement;

		ReplaceFilter(InputStream in
				char replacement) {
			super(in
			this.toReplace = toReplace;
			this.replacement = replacement;
		}

		@Override
		public int run() throws IOException {
			int b = in.read();
			if (b < 0) {
				in.close();
				out.close();
				return -1;
			}
			if ((b & 0xFF) == toReplace) {
				b = replacement;
			}
			out.write(b);
			return 1;
		}
	}

	@Test
	public void testFiltering() throws Exception {
		FilterCommandFactory clean = (repo
			return new ReplaceFilter(in
		};
		FilterCommandFactory smudge = (repo
			return new ReplaceFilter(in
		};
		try (Git git = new Git(db)) {
			Config config = db.getConfig();
			config.setString(ConfigConstants.CONFIG_FILTER_SECTION
					"clean"
			config.setString(ConfigConstants.CONFIG_FILTER_SECTION
					"smudge"
			write(new File(db.getWorkTree()
					"smudgetest filter=a2e");
			git.add().addFilepattern(".gitattributes").call();
			git.commit().setMessage("Attributes").call();
			ApplyResult result = init("smudgetest"
			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree()
					b.getString(0

		} finally {
		}
	}

