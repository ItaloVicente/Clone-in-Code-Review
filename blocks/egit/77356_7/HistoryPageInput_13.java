
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof HistoryPageInput)) {
			return false;
		}
		HistoryPageInput other = (HistoryPageInput) obj;
		return repo == other.repo && singleFile == other.singleFile
				&& singleItem == other.singleItem
				&& listEquals(files, other.files)
				&& listEquals(list, other.list);
	}

	private <T> boolean listEquals(List<? extends T> a, List<? extends T> b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return Arrays.equals(a.toArray(), b.toArray());
	}

	@Override
	public int hashCode() {
		return (repo == null ? 0 : repo.hashCode())
				^ (singleFile == null ? 0 : singleFile.hashCode())
				^ (singleItem == null ? 0 : singleItem.hashCode())
				^ (files == null ? 0 : Arrays.hashCode(files.toArray()))
				^ (list == null ? 0 : Arrays.hashCode(list.toArray()));
	}
