		CancellableDigestOutputStream out = new CancellableDigestOutputStream(m,
				NullOutputStream.INSTANCE);

		byte[] KB = new byte[1024];
		int triggerCancelWriteCnt = BYTES_TO_WRITE_BEFORE_CANCEL_CHECK
				/ KB.length;
		for (int i = 0; i < triggerCancelWriteCnt + 1; i++) {
			out.write(KB);
