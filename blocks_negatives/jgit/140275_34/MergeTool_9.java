	private boolean isLaunch(String toolNamePrompt) throws IOException {
		boolean launch = true;
		outw.print(MessageFormat.format(CLIText.get().mergeToolLaunch,
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		if ((line = br.readLine()) != null) {
				launch = false;
