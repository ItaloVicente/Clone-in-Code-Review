	private boolean promptForLaunch(String toolNamePrompt) {
		try {
			boolean launch = true;
			outw.print(message);
			outw.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));
			String line = null;
			if ((line = br.readLine()) != null) {
					launch = false;
				}
