
		@SuppressWarnings("boxing")
		@Override
		public boolean prompt(String toolToLaunchName) {
			try {
				boolean launchCompare = true;
				outw.println(MessageFormat.format(CLIText.get().diffToolLaunch
						fileIndex
				outw.flush();
				BufferedReader br = inputReader;
				String line = null;
				if ((line = br.readLine()) != null) {
						launchCompare = false;
					}
				}
				return launchCompare;
			} catch (IOException e) {
				throw new IllegalStateException("Cannot output text"
			}
