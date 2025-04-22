
	@Nullable
	public static LfsPointer parseLfsPointer(InputStream in)
			throws IOException {
		boolean v = false;
		LongObjectId id = null;
		long si = -1;

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(in))) {
			for (String s = br.readLine(); s != null; s = br.readLine()) {
					continue;
						&& s.substring(8).trim().equals(VERSION)) {
					v = true;
					id = LongObjectId.fromString(s.substring(4).trim());
					si = Long.parseLong(s.substring(5).trim());
				} else {
					return null;
				}
			}
			if (v && id != null && si > -1) {
				return new LfsPointer(id
			}
		}
		return null;
	}
}
