	@SuppressWarnings("boxing")
	private Map<Integer, List<CommitItem>> getSeverityItems() {
		Map<Integer, List<CommitItem>> result = new HashMap<>();
		for (final CommitItem item : items) {
			if (item.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result.get(item.getProblemSeverity()) == null) {
					List<CommitItem> itemsList = new ArrayList<>();
					itemsList.add(item);
					result.put(item.getProblemSeverity(), itemsList);
				} else {
					result.get(item.getProblemSeverity()).add(item);
				}
			}
		}
		return result;
	}

