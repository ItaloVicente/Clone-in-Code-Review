	@SuppressWarnings("boxing")
	private boolean isLaunchCompare(int fileIndex, int fileCount,
			String fileName, String toolNamePrompt) throws IOException {
		boolean launchCompare = true;
		outw.println(MessageFormat.format(CLIText.get().diffToolLaunch,
				fileIndex, fileCount, fileName, toolNamePrompt));
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
