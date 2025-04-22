		try {
			if ((reuseDeltas || reuseObjects) && reuseSupport != null)
				searchForReuse();

			out = new PackOutputStream(packStream

			int cnt = getObjectsNumber();
			writeMonitor.beginTask(WRITING_OBJECTS_PROGRESS
			out.writeFileHeader(PACK_VERSION_GENERATED
			writeObjects();
			writeChecksum();
			writeMonitor.endTask();
		} finally {
			out = null;
			reader.release();
		}
