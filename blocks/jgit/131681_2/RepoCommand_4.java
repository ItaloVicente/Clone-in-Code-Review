	public final static class RemoteFile {
		private final byte[] contents;

		private final FileMode fileMode;

		public RemoteFile(byte[] contents
			this.contents = contents;
			this.fileMode = fileMode;
		}

		public byte[] getContents() {
			return contents;
		}

		public FileMode getFileMode() {
			return fileMode;
		}

	}

