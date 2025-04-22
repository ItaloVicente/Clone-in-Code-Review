	@SuppressWarnings("nls")
	public static String quotePath(String path) {
		if (path.contains(" ")) {
			if (!path.startsWith("\"")) {
				path = "\"" + path;
			}
			if (!path.endsWith("\"")) {
				path = path + "\"";
			}
		}
		return path;
	}

	public static boolean isToolAvailable(Repository repo
		boolean available = true;
		try {
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
			available = cmdExec.checkExecutable(path
					prepareEnvironment(repo
		} catch (Exception e) {
			available = false;
		}
		return available;
	}

