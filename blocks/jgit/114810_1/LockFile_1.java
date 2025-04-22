		return "LockFile[" + files + "]";
	}

	private static final class Files {
		private final File ref;
		private final File lck;

		public Files(File ref
			this.ref = ref;
			this.lck = lck;
		}

		@Override
		public String toString() {
			return "ref=" + ref + "
		}
