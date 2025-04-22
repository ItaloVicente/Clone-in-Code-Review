		static void deleteOnShutdown(File tmp) {
			synchronized (me) {
				me.toDelete.add(tmp);
			}
		}

		static void removed(File tmp) {
			synchronized (me) {
				me.toDelete.remove(tmp);
			}
		}

		private final List<File> toDelete = new ArrayList<File>();

		@Override
		public void run() {
			System.gc();
			synchronized (this) {
				boolean silent = false;
				boolean failOnError = false;
				for (File tmp : toDelete)
					recursiveDelete(tmp
			}
		}
	}
