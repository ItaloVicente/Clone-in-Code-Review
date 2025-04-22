
	public static String normalizeBranchName(String name
		if (trim) {
			name = name.trim();
		}
		return name.replaceAll("\\s+([_:-])\\s+"
				.replaceAll(":"
				.replaceAll("\\s+"
				.replaceAll("_{2
				.replaceAll("^_"
				.replaceAll("[^\\w-]"
	}
