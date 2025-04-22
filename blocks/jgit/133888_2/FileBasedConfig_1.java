		int retries = MAX_RETRIES;
		FileSnapshot oldSnapshot = snapshot;
		FileSnapshot newSnapshot = null;
		try {
			byte[] in = null;
			LockFile lf = new LockFile(getFile());
			while (in == null) {
				synchronized (this) {
					if (getFile().getParentFile().canWrite()) {
						retries -= lf.lockAndWait(retries
					}
					newSnapshot = FileSnapshot.save(getFile());
					try {
						in = IO.readFully(getFile());
					} catch (FileNotFoundException noFile) {
						if (getFile().exists()) {
							throw noFile;
						}
						clear();
