	@Nullable
	public static LfsPointer parseLfsPointer(InputStream in)
			throws IOException {
		boolean versionLine = false;
		LongObjectId id = null;
		long sz = -1;

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(in))) {
			for (String s = br.readLine(); s != null; s = br.readLine()) {
					continue;
						&& s.substring(8).trim().equals(VERSION)) {
					versionLine = true;
					id = LongObjectId.fromString(s.substring(11).trim());
					sz = Long.parseLong(s.substring(5).trim());
				} else {
					return null;
				}
			}
			if (versionLine && id != null && sz > -1) {
				return new LfsPointer(id
			}
		}
		return null;
	}

