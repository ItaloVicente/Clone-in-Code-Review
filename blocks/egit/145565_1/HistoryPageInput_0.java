		int result = Objects.hash(repo, singleFile, singleItem);
		if (files != null) {
			result = 31 * result + Arrays.hashCode(files.toArray());
		}
		if (list != null) {
			result = 31 * result + Arrays.hashCode(list.toArray());
		}
		return result;
