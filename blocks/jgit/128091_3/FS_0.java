	public static class LockToken implements Closeable {

		private boolean isCreated;

		private Optional<Path> link;

		LockToken(boolean isCreated
			this.isCreated = isCreated;
			this.link = link;
		}

		public boolean isCreated() {
			return isCreated;
		}

		@Override
		public void close() {
			if (link.isPresent()) {
				try {
					Files.delete(link.get());
				} catch (IOException e) {
					LOG.error(MessageFormat.format(JGitText.get().closeLockTokenFailed
							this)
				}
			}
		}

		@Override
		public String toString() {
					"
		}
	}

	public LockToken createNewFileAtomic(File path) throws IOException {
		return new LockToken(path.createNewFile()
	}

