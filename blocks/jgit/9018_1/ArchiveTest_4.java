	}

	private void grepForEntry(String name
			throws Exception {
		final Process proc = spawnAssumingCommandPresent(cmdline);
		proc.getOutputStream().close();
		final BufferedReader reader = readFromProcess(proc);
