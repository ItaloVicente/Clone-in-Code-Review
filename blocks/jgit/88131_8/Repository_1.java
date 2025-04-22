
	public static String normalizeBranchName(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		String result = name.trim();
		return result.replaceAll("\\s+([_:-])*?\\s+"
				.replaceAll(":"
				.replaceAll("\\s+"
				.replaceAll("_{2
				.replaceAll("-{2
				.replaceAll("[^\\w-]"
				.replaceAll("^_+"
				.replaceAll("^-+"
	}
