
		public RemoteFile readFileWithMode(String uri
				throws GitAPIException
	}

	public static class RemoteFile {
		private final byte[] contents;

		private final FileMode fileMode;

		RemoteFile(byte[] contents
			this.contents = contents;
			this.fileMode = fileMode;
		}

		byte[] getContents() {
			return contents;
		}

		FileMode getFileMode() {
			return fileMode;
		}

