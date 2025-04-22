	public static final byte[] readSome(final File path
			throws FileNotFoundException
		FileInputStream in = new FileInputStream(path);
		try {
			byte[] buf = new byte[limit];
			int cnt = 0;
			for (;;) {
				int n = in.read(buf
				if (n <= 0)
					break;
				cnt += n;
			}
			if (cnt == buf.length)
				return buf;
			byte[] res = new byte[cnt];
			System.arraycopy(buf
			return res;
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
			}
		}
	}

