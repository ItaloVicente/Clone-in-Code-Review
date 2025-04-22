	@SuppressWarnings("boxing")
	private boolean isLaunchCompare(int fileIndex, int fileCount,
			String fileName, String toolNamePrompt) throws IOException {
		boolean launchCompare = true;
		outw.println(MessageFormat.format(CLIText.get().diffToolLaunch,
				fileIndex, fileCount, fileName, toolNamePrompt) + " "); //$NON-NLS-1$
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}
