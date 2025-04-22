
	public static String normalizeBranchName(String name
		String result = name;
		if (trim) {
			result = name.trim();
		}
		return result.replaceAll("\\s+([_:-])\\s+"
				.replaceAll(":"
				.replaceAll("\\s+"
				.replaceAll("_{2
				.replaceAll("^_"
				.replaceAll("[^\\w-]"
	}
