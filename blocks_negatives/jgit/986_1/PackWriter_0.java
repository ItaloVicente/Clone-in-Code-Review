		if ((reuseDeltas || reuseObjects) && reuseSupport != null)
			searchForReuse();

		out = new PackOutputStream(packStream, isDeltaBaseAsOffset());

		writeMonitor.beginTask(WRITING_OBJECTS_PROGRESS, getObjectsNumber());
		out.writeFileHeader(PACK_VERSION_GENERATED, getObjectsNumber());
		writeObjects();
		writeChecksum();

		out = null;
		reader.release();
		writeMonitor.endTask();
