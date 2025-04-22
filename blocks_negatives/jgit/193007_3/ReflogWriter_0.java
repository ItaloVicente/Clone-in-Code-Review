		File log = refdb.logFor(refName);
		boolean write = forceWrite
				|| shouldAutoCreateLog(refName)
				|| log.isFile();
		if (!write)
			return this;

		WriteConfig wc = refdb.getRepository().getConfig().get(WriteConfig.KEY);
		try (FileOutputStream out = getFileOutputStream(log)) {
			if (wc.getFSyncRefFiles()) {
				FileChannel fc = out.getChannel();
				ByteBuffer buf = ByteBuffer.wrap(rec);
				while (0 < buf.remaining()) {
					fc.write(buf);
