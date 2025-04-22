	public static void main(String[] args) throws Exception {
		String repoPath = System.getProperty("git_repo");
		if (repoPath == null) {
			repoPath = ".";
			System.out.println(
					"System property '-Dgit_repo' not specified
							+ new File(repoPath).getAbsolutePath());
		}
		try (Repository db = new FileRepository(repoPath + "/.git")) {
			for (String cmd : args) {
				List<String> result = execute(cmd
				for (String line : result) {
					System.out.println(line);
				}
			}
		}
	}

