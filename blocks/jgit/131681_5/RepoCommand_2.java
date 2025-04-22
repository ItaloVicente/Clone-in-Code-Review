
		default RemoteFile readFileWithMode(String uri
				throws GitAPIException
			return new RemoteFile(readFile(uri
					FileMode.REGULAR_FILE);
		}
	}

	public static final class RemoteFile {
		@NonNull
		private final byte[] contents;

		@NonNull
		private final FileMode fileMode;

		public RemoteFile(@NonNull byte[] contents
				@NonNull FileMode fileMode) {
			this.contents = Objects.requireNonNull(contents);
			this.fileMode = Objects.requireNonNull(fileMode);
		}

		@NonNull
		public byte[] getContents() {
			return contents;
		}

		@NonNull
		public FileMode getFileMode() {
			return fileMode;
		}

