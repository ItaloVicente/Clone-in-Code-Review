	@Test
	public void testCrLf() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, true);
			ApplyResult result = init("crlf", true, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "crlf"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "crlf"),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfOff() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, false);
			ApplyResult result = init("crlf", true, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "crlf"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "crlf"),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfEmptyCommitted() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, true);
			ApplyResult result = init("crlf3", true, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "crlf3"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "crlf3"),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testCrLfNewFile() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, true);
			ApplyResult result = init("crlf4", false, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "crlf4"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "crlf4"),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testPatchWithCrLf() throws Exception {
		try {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, false);
			ApplyResult result = init("crlf2", true, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "crlf2"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "crlf2"),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	@Test
	public void testPatchWithCrLf2() throws Exception {
		String name = "crlf2";
		try (Git git = new Git(db)) {
			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, false);
			a = new RawText(readFile(name + "_PreImage"));
			write(new File(db.getWorkTree(), name),
					a.getString(0, a.size(), false));

			git.add().addFilepattern(name).call();
			git.commit().setMessage("PreImage").call();

			b = new RawText(readFile(name + "_PostImage"));

			db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, true);
			ApplyResult result = git.apply()
					.setPatch(getTestResource(name + ".patch")).call();
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), name),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), name),
					b.getString(0, b.size(), false));
		} finally {
			db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF);
		}
	}

	private static class ReplaceFilter extends FilterCommand {

		private final char toReplace;

		private final char replacement;

		ReplaceFilter(InputStream in, OutputStream out, char toReplace,
				char replacement) {
			super(in, out);
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
		FilterCommandFactory clean = (repo, in, out) -> {
			return new ReplaceFilter(in, out, 'A', 'E');
		};
		FilterCommandFactory smudge = (repo, in, out) -> {
			return new ReplaceFilter(in, out, 'E', 'A');
		};
		try (Git git = new Git(db)) {
			Config config = db.getConfig();
			config.setString(ConfigConstants.CONFIG_FILTER_SECTION, "a2e",
					"clean", "jgit://builtin/a2e/clean");
			config.setString(ConfigConstants.CONFIG_FILTER_SECTION, "a2e",
					"smudge", "jgit://builtin/a2e/smudge");
			write(new File(db.getWorkTree(), ".gitattributes"),
					"smudgetest filter=a2e");
			git.add().addFilepattern(".gitattributes").call();
			git.commit().setMessage("Attributes").call();
			ApplyResult result = init("smudgetest", true, true);
			assertEquals(1, result.getUpdatedFiles().size());
			assertEquals(new File(db.getWorkTree(), "smudgetest"),
					result.getUpdatedFiles().get(0));
			checkFile(new File(db.getWorkTree(), "smudgetest"),
					b.getString(0, b.size(), false));

		} finally {
		}
	}

