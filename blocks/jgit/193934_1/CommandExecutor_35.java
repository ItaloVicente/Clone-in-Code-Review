	public boolean checkExecutable(String path
			Map<String
			throws ToolException
		checkUseMsys2(path);
		String command = null;
		if (fs instanceof FS_Win32 && !useMsys2) {
			Path p = Paths.get(path);
			if (p.isAbsolute() && Files.isExecutable(p)) {
				return true;
			}
		} else {
		}
		boolean available = true;
		try {
			ExecutionResult rc = run(command
			if (rc.getRc() != 0) {
				available = false;
			}
		} catch (IOException | InterruptedException | NoWorkTreeException
				| ToolException e) {
		}
		return available;
	}

