	private void extractBase(final WindowCursor wc
			throws IOException
		File dir = basePath.getParentFile();
		if (!dir.exists())
			dir.mkdirs();

		boolean ok = false;
		File tmp = File.createTempFile("base"
		try {
			ObjectStream in = pack.load(wc
			try {
				if (type == Constants.OBJ_BAD)
					type = in.getType();

				long cnt = 0;
				final long len = in.getSize();
				FileOutputStream out = new FileOutputStream(tmp);
				try {
					byte[] buf = new byte[8192];
					for (;;) {
						int n = in.read(buf);
						if (n < 0)
							break;
						out.write(buf
						cnt += n;
					}
				} finally {
					out.close();
