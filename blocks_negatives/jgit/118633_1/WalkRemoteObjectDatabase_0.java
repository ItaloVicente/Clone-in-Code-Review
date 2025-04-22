		try {
			final BufferedReader br = openReader(ROOT_DIR
					+ Constants.PACKED_REFS);
			try {
				readPackedRefsImpl(avail, br);
			} finally {
				br.close();
			}
