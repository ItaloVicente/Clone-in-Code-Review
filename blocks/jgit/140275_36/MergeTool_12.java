	private boolean promptForLaunch(String toolNamePrompt) {
		try {
			boolean launch = true;
			outw.print(MessageFormat.format(CLIText.get().mergeToolLaunch
			outw.flush();
			BufferedReader br = inputReader;
			String line = null;
			if ((line = br.readLine()) != null) {
					launch = false;
				}
