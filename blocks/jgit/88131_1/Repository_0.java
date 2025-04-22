
	public static String normalizeBranchName(String name
		if (trim) {
			name = name.trim();
		}
		return name.replaceAll("\\s([_:-])\\s"
				.replaceAll("\\s+"
				.replaceAll("[^\\w-]"
	}
