		try {
			if ((reuseDeltas || reuseObjects) && reuseSupport != null)
				searchForReuse();

			out = new PackOutputStream(packStream, isDeltaBaseAsOffset());

			int cnt = getObjectsNumber();
			writeMonitor.beginTask(WRITING_OBJECTS_PROGRESS, cnt);
			out.writeFileHeader(PACK_VERSION_GENERATED, cnt);
			writeObjects();
			writeChecksum();
			writeMonitor.endTask();
		} finally {
			out = null;
			reader.release();
		}
