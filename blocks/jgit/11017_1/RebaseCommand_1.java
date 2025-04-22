
	private static class RebaseState {

		private final File repoDirectory;
		private File dir;

		public RebaseState(File repoDirectory) {
			this.repoDirectory = repoDirectory;
		}

		public File getDir() {
			if (dir == null) {
				File rebaseApply = new File(repoDirectory
				if (rebaseApply.exists()) {
					dir = rebaseApply;
				} else {
					File rebaseMerge = new File(repoDirectory
					dir = rebaseMerge;
				}
			}
			return dir;
		}

		public String readFile(String name) throws IOException {
			return readFile(getDir()
		}

		public void createFile(String name
			createFile(getDir()
		}

		public File getFile(String name) {
			return new File(getDir()
		}

		private static String readFile(File directory
				throws IOException {
			byte[] content = IO.readFully(new File(directory
			int end = content.length;
			while (0 < end && content[end - 1] == '\n')
				end--;
			return RawParseUtils.decode(content
		}

		private static void createFile(File parentDir
				String content)
				throws IOException {
			File file = new File(parentDir
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(content.getBytes(Constants.CHARACTER_ENCODING));
				fos.write('\n');
			} finally {
				fos.close();
			}
		}
	}
