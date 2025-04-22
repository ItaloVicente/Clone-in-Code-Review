		int retries = MAX_RETRIES;
		FileSnapshot oldSnapshot = snapshot;
		FileSnapshot newSnapshot = null;
		try {
			byte[] in = null;
			LockFile lf = new LockFile(getFile());
			while (true) {
				synchronized (this) {
					if (getFile().getParentFile().canWrite()) {
						retries -= lf.lockAndWait(retries
					}
					newSnapshot = FileSnapshot.save(getFile());
					try {
						in = IO.readFully(getFile());
						break;
					} catch (FileNotFoundException noFile) {
						if (getFile().exists()) {
							throw noFile;
						}
						clear();
