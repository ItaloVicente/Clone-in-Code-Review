	public static void main(String[] args) throws Exception {
		String workDir = System.getProperty("git_work_tree");
		if (workDir == null) {
			workDir = ".";
			System.out.println(
					"System property 'git_work_tree' not specified
							+ new File(workDir).getAbsolutePath());
		}
		try (Repository db = new FileRepository(workDir + "/.git")) {
			for (String cmd : args) {
				List<String> result = execute(cmd
				for (String line : result) {
					System.out.println(line);
				}
			}
		}
