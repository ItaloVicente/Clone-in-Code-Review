	public static boolean isEqualOrPrefix(String folder
		if (folder.isEmpty()) {
			return path.isEmpty();
		}
		boolean isPrefix = path.startsWith(folder);
		if (isPrefix) {
			int length = folder.length();
			return path.length() == length || path.charAt(length) == '/';
		}
		return false;
	}

