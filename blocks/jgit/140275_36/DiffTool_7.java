		} catch (IOException e) {
			throw new IllegalStateException("Cannot output text"
		}
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
