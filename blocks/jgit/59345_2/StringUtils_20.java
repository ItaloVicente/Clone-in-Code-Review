
	public static List<String> splitPath(String path) {
		ArrayList<String> list = new ArrayList<>();
		if (path == null)
			return list;
		int len = path.length();
		int pos = 0;
		int next = path.indexOf('/'
		while (next >= 0) {
			if (next - pos > 0) {
				list.add(path.substring(pos
			}
			pos = next + 1;
			next = path.indexOf('/'
		}
		if (pos < len) {
			list.add(path.substring(pos
		}

		return list;
	}
