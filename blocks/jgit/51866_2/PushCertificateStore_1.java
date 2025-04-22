	public void save(BatchRefUpdate batch) throws IOException {
		ObjectId newId = write();
		if (newId == null) {
			return;
		}
		batch.addCommand(new ReceiveCommand(
				commit != null ? commit : ObjectId.zeroId()
	}

	public void clear() {
		pending.clear();
	}

	private ObjectId write() throws IOException {
		if (pending.isEmpty()) {
			return null;
		}
		if (reader == null) {
			load();
		}
		sortPending(pending);

		ObjectId curr = commit;
		DirCache dc = newDirCache();
		try (ObjectInserter inserter = db.newObjectInserter()) {
			for (PendingCert pc : pending) {
				curr = saveCert(inserter
			}
			inserter.flush();
			return curr;
		}
	}

