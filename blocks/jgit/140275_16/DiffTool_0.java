	}

	private class CountingPromptContinueHandler
			implements PromptContinueHandler {
		private final int fileIndex;

		private final int fileCount;

		private final String fileName;

		public CountingPromptContinueHandler(int fileIndex
				String fileName) {
			this.fileIndex = fileIndex;
			this.fileCount = fileCount;
			this.fileName = fileName;
		}

		@Override
		public boolean prompt(String toolToLaunchName) {
			try {
				boolean launchCompare = true;
				outw.flush();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(ins));
				String line = null;
				if ((line = br.readLine()) != null) {
						launchCompare = false;
					}
				}
				return launchCompare;
			} catch (IOException e) {
				throw new IllegalStateException("Cannot output text"
			}
