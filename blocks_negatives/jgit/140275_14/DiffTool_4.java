	private boolean isLaunchCompare(int fileIndex, int fileCount,
			String fileName, String toolNamePrompt) throws IOException {
		boolean launchCompare = true;
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}

