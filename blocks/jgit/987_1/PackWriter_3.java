		if ((reuseDeltas || reuseObjects) && reuseSupport != null)
			searchForReuse();

		out = new PackOutputStream(packStream

		writeMonitor.beginTask(WRITING_OBJECTS_PROGRESS
		out.writeFileHeader(PACK_VERSION_GENERATED
		writeObjects();
		writeChecksum();

		out = null;
		reader.release();
		writeMonitor.endTask();
	}

	public void release() {
		reader.release();
