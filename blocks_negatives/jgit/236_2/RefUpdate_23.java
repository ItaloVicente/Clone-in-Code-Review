		abstract Result store(final LockFile lock, final Result status)
				throws IOException;
	}

	class UpdateStore extends Store {

		@Override
		Result store(final LockFile lock, final Result status)
				throws IOException {
			return updateStore(lock, status);
		}
	}

	class DeleteStore extends Store {

		@Override
		Result store(LockFile lock, Result status) throws IOException {
			Storage storage = ref.getStorage();
			if (storage == Storage.NEW)
				return status;
			if (storage.isPacked())
				db.removePackedRef(ref.getName());

			final int levels = count(ref.getName(), '/') - 2;

			final File gitDir = db.getRepository().getDirectory();
			final File logDir = new File(gitDir, Constants.LOGS);
			deleteFileAndEmptyDir(new File(logDir, ref.getName()), levels);

			lock.unlock();
			if (storage.isLoose())
				deleteFileAndEmptyDir(looseFile, levels);
			db.uncacheRef(ref.getName());
			return status;
		}

		private void deleteFileAndEmptyDir(final File file, final int depth)
				throws IOException {
			if (file.isFile()) {
				if (!file.delete())
					throw new IOException("File cannot be deleted: " + file);
				File dir = file.getParentFile();
				for  (int i = 0; i < depth; ++i) {
					if (!dir.delete())
					dir = dir.getParentFile();
				}
			}
		}
	}

	UpdateStore newUpdateStore() {
		return new UpdateStore();
	}

	DeleteStore newDeleteStore() {
		return new DeleteStore();
	}

	static void deleteEmptyDir(File dir, int depth) {
		for (; depth > 0 && dir != null; depth--) {
			if (dir.exists() && !dir.delete())
				break;
			dir = dir.getParentFile();
		}
	}

	static int count(final String s, final char c) {
		int count = 0;
		for (int p = s.indexOf(c); p >= 0; p = s.indexOf(c, p + 1)) {
			count++;
		}
		return count;
